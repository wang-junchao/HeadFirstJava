package MIDIMusic;

import javax.sound.midi.*;

public class MiniMiniMusicApp {
	public static void main(String[] args){
		MiniMiniMusicApp mini=new MiniMiniMusicApp();
		mini.play();
	}
	public void play(){
		try{
			//ȡ��Sequencer�������
			Sequencer player=MidiSystem.getSequencer();
			player.open();
			
			Sequence seq= new Sequence(Sequence.PPQ, 4);
			
			Track track=seq.createTrack(); //Ҫ��ȡ��Track
			
			//��Track���뼸��MidiEvent��Ҫע�����setMessage()�Ĳ������Լ� MidiEvent��constructor
			ShortMessage a =new ShortMessage();
			a.setMessage(144,1,44,100);
			MidiEvent noteOn =new MidiEvent(a, 3);
			track.add(noteOn);
			
			ShortMessage b=new ShortMessage();
			b.setMessage(129,1,44,100);
			MidiEvent noteOff=new MidiEvent(b,16);
			track.add(noteOff);
			
			player.setSequence(seq);		//��Sequence�͵�Sequencer��
			
			player.start();		//��ʼ����
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
