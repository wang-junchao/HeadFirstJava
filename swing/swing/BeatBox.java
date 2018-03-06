package swing;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
public class BeatBox {
	
	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	Sequencer sequencer;
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
		new BeatBox().buildGUI();
	}
	
	public void buildGUI(){
		theFrame=new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		setUpMidi();
		
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
		int[] trackList=null;			//创建出16个元素的数组来存储一项乐器的值。如果该节应该要演奏，其值回事关键字值，否则为零。
		
		//清除掉旧的track做一个心的
		sequence.deleteTrack(track);
		track=sequence.createTrack();
		
		for(int i=0;i<16;i++){			//对每个乐器都执行一次
			trackList=new int[16];	
			
			int key =instruments[i];		//设定代表乐器的关键字
			
			for(int j=0;j<16;j++){
				JCheckBox jc=(JCheckBox) checkboxList.get(j+(16*i));
				//如果有勾选，将关键字值放到数组的该位置上，不然就补零
				if(jc.isSelected()){
					trackList[j]=key;
				}else{
					trackList[j]=0;
				}
			}
			
			makeTracks(trackList);	//创建此乐器的事件并加到track上
			track.add(makeEvent(176,1,127,0,16));
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
	
	public void makeTracks(int[] list){			//创建某项乐器的所有事件
		
		for (int i=0;i<16;i++){
			int key=list[i];
			
			if(key!=0){
				//创建noteon和noteoff事件并加到track上
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100,i+1));
				
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
	
	public class MySendListener implements ActionListener{
		public void actionPerformed(ActionEvent a){	//用户按下按钮触发ActionEvent时执行
			boolean[] checkboxState=new boolean[256];	//用来保存复选框的状态
			for(int i=0;i<256;i++){
				JCheckBox check=(JCheckBox) checkboxList.get(i);	//取得的状态加到数组
				if(check.isSelected()){
					checkboxState[i]=true;
				}
			}
			
			try{
				FileOutputStream fileStream=new FileOutputStream(new File("Checkbox.ser"));
				ObjectOutputStream os=new ObjectOutputStream(fileStream);
				os.writeObject(checkboxState);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	public class MyReadInListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState=null;
			try{
				FileInputStream fileIn=new FileInputStream(new File("Checkbox.ser"));
				ObjectInputStream is=new ObjectInputStream(fileIn);
				checkboxState=(boolean[]) is.readObject();		//读取文件中的对象并读取回来的object类型换回boolean数组
			}catch(Exception ex){ex.printStackTrace();
		}
			for(int i=0;i<256;i++){
				JCheckBox check =(JCheckBox)checkboxList.get(i);
				if(checkboxState[i]){
					check.setSelected(true);		//还原每个checkbox的状态
				}else{
					check.setSelected(false);
				}
			}
			
			sequencer.stop();
			buildTrackAndStart();		//停止目前播放的节奏并使用复选框重新创建序列
		}
	}
}
