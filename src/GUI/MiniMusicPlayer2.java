package GUI;
import javax.sound.midi.*;
public class MiniMusicPlayer2 implements ControllerEventListener {
	public static void main(String[] args){
		MiniMusicPlayer2 mini=new MiniMusicPlayer2();
		mini.go();
	}
	public void go(){
		try{
			//创建队列并打开
			Sequencer sequencer=MidiSystem.getSequencer();
			sequencer.open();
			
			int[] eventsIwant={127}; 
			sequencer.addControllerEventListener(this, eventsIwant);
			Sequence seq=new Sequence(Sequence.PPQ,4);  //创建队列并track
			Track track=seq.createTrack();
			
			for(int i=5;i<61;i+=4){
				
				track.add(makeEvent(144,1,i,100,i));
				track.add(makeEvent(176,1,127,0,i));  //插入事件编号为127的自定义ControllerEvent(176), 它不会做任何事情，只是让我们知道有音符被播放，因为它的tick跟Note on是同时进行的
				track.add(makeEvent(128,1,i,100,i+2));
			}
			
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(220);
			sequencer.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void controlChange(ShortMessage event){
		System.out.println("la");    //获知实践时在命令打出字符串的事件处理程序);
	}
	
	public static MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
		MidiEvent event=null;
		try{
			ShortMessage a =new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event=new MidiEvent(a,tick);
		}catch(Exception e){}
			return event;
	}
}
