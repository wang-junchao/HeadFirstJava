package BeatBoxFinal;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;
public class BeatBoxFinal {
	
	JList incomingList;
	JTextField userMessage;
	int nextNum;
	Vector<String> listVector=new Vector<String>();
	String userName;
	ObjectOutputStream out;
	ObjectInputStream in;
	HashMap<String,boolean[]> otherSeqsMap=new HashMap<String, boolean[]>();
	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	Sequencer sequencer;
	Sequence mySequence=null;
	Sequence sequence;
	Track track;
	JFrame theFrame;
	
	//乐器的名称 以String的array维护
	String[] instrumentNames={" Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Hand Clap",
			"High Tom","Hi Bongo","Naracas","Whistle","Low Conga",
			"Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
	//实际的乐器关键字，例如说35是bass 42是Closed Hi-Hat
	int[] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	
	public static void main(String[] args){
		//作为显示名称的命令栏参数
		new BeatBoxFinal().startUp(args[0]);
	}
	public void startUp(String name){
		userName=name;
		//open connection to the server
		//设置网络、输入/输出，并创建出reader的线程
		try{
			Socket sock=new Socket("127.0.0.1",4242);
			out=new ObjectOutputStream(sock.getOutputStream());
			in=new ObjectInputStream(sock.getInputStream());
			Thread remote=new Thread(new RemoteReader());
			remote.start();
		}catch(Exception ex){
			System.out.println("couldn't connect - you'll have to play alone.");
		}
		setUpMidi();
		buildGUI();
	}
	
	public void buildGUI(){
		
		theFrame=new JFrame("Cyber BeatBox");
		BorderLayout layout=new BorderLayout();
		JPanel background=new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  //设定面板上摆组件时的空白边缘
		
		checkboxList=new ArrayList<JCheckBox>();
		Box buttonBox=new Box(BoxLayout.Y_AXIS);
		
		JButton start =new JButton("Start");
		start.addActionListener(new MyStartListener());
		buttonBox.add(start);
		
		JButton stop =new JButton("stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);
		
		JButton upTempo=new JButton("Tempo Up");
		upTempo.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTempo);
		
		JButton downTempo =new JButton("Tempo Down");
		downTempo.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);
		
		JButton sendIt=new JButton("sendIt");
		sendIt.addActionListener(new MySendListener());
		buttonBox.add(sendIt);
		
		userMessage=new JTextField();
		
		buttonBox.add(userMessage);
		
		//会显示受到信息的组件
		incomingList=new JList();
		incomingList.addListSelectionListener(new MyListSelectionListener());
		incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane theList=new JScrollPane(incomingList);
		buttonBox.add(theList);
		incomingList.setListData(listVector);	//no data to start with
		
		Box nameBox=new Box(BoxLayout.Y_AXIS);
		for(int i=0;i<16;i++){
			nameBox.add(new Label(instrumentNames[i]));
		}
		
		background.add(BorderLayout.EAST,buttonBox);
		background.add(BorderLayout.WEST,nameBox);
		
		theFrame.getContentPane().add(background);
		
		GridLayout grid=new GridLayout(16,16);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel=new JPanel(grid);
		background.add(BorderLayout.CENTER,mainPanel);
		
		//创建checkbox组，设定为未勾选为false并加到ArraList和面板上
		for(int i=0;i<256;i++){
			JCheckBox c=new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);  
		}
		
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}
	//一般的MiDi设置程序代码
	public void setUpMidi(){
		try{
			sequencer=MidiSystem.getSequencer();
			sequencer.open();
			sequence=new Sequence(Sequence.PPQ,4);
			track=sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//此处会将复选框状态转换为MiDi时间并加到track上
	public void buildTrackAndStart(){
		
		ArrayList<Integer> trackList=null;			//创建出16个元素的数组来存储一项乐器的值。如果该节应该要演奏，其值回事关键字值，否则为零。
		
		//清除掉旧的track做一个心的
		sequence.deleteTrack(track);
		track=sequence.createTrack();
		
		for(int i=0;i<16;i++){			//对每个乐器都执行一次
			trackList=new ArrayList<Integer>();	
		
			for(int j=0;j<16;j++){
				JCheckBox jc=(JCheckBox) checkboxList.get(j+(16*i));
				//如果有勾选，将关键字值放到数组的该位置上，不然就补零
				if(jc.isSelected()){
					int key =instruments[i];
					trackList.add(new Integer(key));
				}else{
					trackList.add(null);   //because this slot should be empty in the track
				}
			}
			
			makeTracks(trackList);	//创建此乐器的事件并加到track上
		}
		
		track.add(makeEvent(192,9,1,0,15));		//确保第16拍有事件，否则beatbox不会重复播放
		try{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);   //指定无穷的重复次数
			//开始播放
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public class MyStartListener implements ActionListener{
		//第一个内部类，按钮的监听者
		public void actionPerformed(ActionEvent a ){
			buildTrackAndStart();
		}
	}
	
	//另一个内部类，也是按钮的监听者   
	//节奏银子，预设为1.0 每次调整3%
	public class MyStopListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			sequencer.stop();
		}
	}
	public class MyUpTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor=sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));
		}
	}
	
	public class MyDownTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor=sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * .97));
			
		}
	}
	
	public class MySendListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			//make an arraylist of just the STATE of the checkboxes
			boolean[] checkboxState=new boolean[256];
			for(int i=0;i<256;i++){
				JCheckBox check=(JCheckBox) checkboxList.get(i);
				if (check.isSelected()){
					checkboxState[i]=true;
				}
			}
		String messageToSend=null;
		try{
			out.writeObject(userName+nextNum++ + ": " + userMessage.getText());
			out.writeObject(checkboxState);
		}catch(Exception ex){
			System.out.println("Sorry dude. Could not send it to the server.");
		}
		userMessage.setText("");
		}
	}
	
	public class MyListSelectionListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent le){
			if(!le.getValueIsAdjusting()){
				String selected=(String) incomingList.getSelectedValue();
				if(selected!=null){
					//now go to the map, and change the sequence
					boolean[] selectedState=(boolean[]) otherSeqsMap.get(selected);
					changeSequence(selectedState);
					sequencer.stop();
					buildTrackAndStart();
				}
			}
		}
	}
	
	public class RemoteReader implements Runnable{
		boolean[] checkboxState=null;
		String nameToShow=null;
		Object obj=null;
		public void run(){
			try{
				while((obj=in.readObject())!=null){
					System.out.println("got an object form server");
					System.out.println(obj.getClass());
					String nameToShow=(String) obj;
					checkboxState=(boolean[]) in.readObject();
					otherSeqsMap.put(nameToShow, checkboxState);
					listVector.add(nameToShow);
					incomingList.setListData(listVector);
				}
			}catch(Exception ex){ex.printStackTrace();}
		}
	}
	
	public class MyPlayMineListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			if(mySequence!=null){
				sequence=mySequence;	//restore to my original
			}
		}
	}
	
	public void changeSequence(boolean[] checkboxState){
		for(int i=0;i<256;i++){
			JCheckBox check=(JCheckBox) checkboxList.get(i);
			if(checkboxState[i]){
				check.setSelected(true);
			}else{
				check.setSelected(false);
			}
		}
	}
	
	public void makeTracks(ArrayList list){			//创建某项乐器的所有事件
		Iterator it=list.iterator();
		for (int i=0;i<16;i++){
			Integer num=(Integer) it.next();
			if(num!=null){
				int numKey=num.intValue();
				//创建noteon和noteoff事件并加到track上
				track.add(makeEvent(144,9,numKey,100,i));
				track.add(makeEvent(128,9,numKey,100,i+1));
				
			}
		}
	}
	
	public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
		MidiEvent event=null;
		try{
			ShortMessage a=new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event=new MidiEvent(a,tick);
		}catch(Exception e){e.printStackTrace();}
		return event;
	}
}
