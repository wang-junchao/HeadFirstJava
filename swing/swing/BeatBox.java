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
	
	//���������� ��String��arrayά��
	String[] instrumentNames={" Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Hand Clap",
			"High Tom","Hi Bongo","Naracas","Whistle","Low Conga",
			"Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
	//ʵ�ʵ������ؼ��֣�����˵35��bass 42��Closed Hi-Hat
	int[] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	
	public static void main(String[] args){
		new BeatBox().buildGUI();
	}
	
	public void buildGUI(){
		theFrame=new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout=new BorderLayout();
		JPanel background=new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  //�趨����ϰ����ʱ�Ŀհױ�Ե
		
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
		
		//����checkbox�飬�趨Ϊδ��ѡΪfalse���ӵ�ArraList�������
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
	//һ���MiDi���ó������
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
	
	//�˴��Ὣ��ѡ��״̬ת��ΪMiDiʱ�䲢�ӵ�track��
	public void buildTrackAndStart(){
		int[] trackList=null;			//������16��Ԫ�ص��������洢һ��������ֵ������ý�Ӧ��Ҫ���࣬��ֵ���¹ؼ���ֵ������Ϊ�㡣
		
		//������ɵ�track��һ���ĵ�
		sequence.deleteTrack(track);
		track=sequence.createTrack();
		
		for(int i=0;i<16;i++){			//��ÿ��������ִ��һ��
			trackList=new int[16];	
			
			int key =instruments[i];		//�趨���������Ĺؼ���
			
			for(int j=0;j<16;j++){
				JCheckBox jc=(JCheckBox) checkboxList.get(j+(16*i));
				//����й�ѡ�����ؼ���ֵ�ŵ�����ĸ�λ���ϣ���Ȼ�Ͳ���
				if(jc.isSelected()){
					trackList[j]=key;
				}else{
					trackList[j]=0;
				}
			}
			
			makeTracks(trackList);	//�������������¼����ӵ�track��
			track.add(makeEvent(176,1,127,0,16));
		}
		
		track.add(makeEvent(192,9,1,0,15));		//ȷ����16�����¼�������beatbox�����ظ�����
		try{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);   //ָ��������ظ�����
			//��ʼ����
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public class MyStartListener implements ActionListener{
		//��һ���ڲ��࣬��ť�ļ�����
		public void actionPerformed(ActionEvent a ){
			buildTrackAndStart();
		}
	}
	
	//��һ���ڲ��࣬Ҳ�ǰ�ť�ļ�����   
	//�������ӣ�Ԥ��Ϊ1.0 ÿ�ε���3%
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
	
	public void makeTracks(int[] list){			//����ĳ�������������¼�
		
		for (int i=0;i<16;i++){
			int key=list[i];
			
			if(key!=0){
				//����noteon��noteoff�¼����ӵ�track��
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
		public void actionPerformed(ActionEvent a){	//�û����°�ť����ActionEventʱִ��
			boolean[] checkboxState=new boolean[256];	//�������渴ѡ���״̬
			for(int i=0;i<256;i++){
				JCheckBox check=(JCheckBox) checkboxList.get(i);	//ȡ�õ�״̬�ӵ�����
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
				checkboxState=(boolean[]) is.readObject();		//��ȡ�ļ��еĶ��󲢶�ȡ������object���ͻ���boolean����
			}catch(Exception ex){ex.printStackTrace();
		}
			for(int i=0;i<256;i++){
				JCheckBox check =(JCheckBox)checkboxList.get(i);
				if(checkboxState[i]){
					check.setSelected(true);		//��ԭÿ��checkbox��״̬
				}else{
					check.setSelected(false);
				}
			}
			
			sequencer.stop();
			buildTrackAndStart();		//ֹͣĿǰ���ŵĽ��ಢʹ�ø�ѡ�����´�������
		}
	}
}
