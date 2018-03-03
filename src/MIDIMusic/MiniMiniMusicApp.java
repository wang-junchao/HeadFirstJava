package MIDIMusic;

import javax.sound.midi.*;

public class MiniMiniMusicApp {
	public static void main(String[] args){
		MiniMiniMusicApp mini=new MiniMiniMusicApp();
		mini.play();
	}
	public void play(){
		try{
			//取得Sequencer并将其打开
			Sequencer player=MidiSystem.getSequencer();
			player.open();
			
			Sequence seq= new Sequence(Sequence.PPQ, 4);
			
			Track track=seq.createTrack(); //要求取得Track
			
			//对Track加入几个MidiEvent，要注意的是setMessage()的参数，以及 MidiEvent的constructor
			ShortMessage a =new ShortMessage();
			a.setMessage(144,1,44,100);
			MidiEvent noteOn =new MidiEvent(a, 3);
			track.add(noteOn);
			
			ShortMessage b=new ShortMessage();
			b.setMessage(129,1,44,100);
			MidiEvent noteOff=new MidiEvent(b,16);
			track.add(noteOff);
			
			player.setSequence(seq);		//将Sequence送到Sequencer上
			
			player.start();		//开始播放
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
