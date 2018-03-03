package MIDIMusic;
import javax.sound.midi.*;	//引用该包

public class MusicTest1 {
	public void play(){
		try{
			//对象作用是将MiDi信息组合成乐曲
			Sequencer sequencer =MidiSystem.getSequencer();	//把有风险的程序放在try块
			System.out.println("We got a sequencer");
		}catch(MidiUnavailableException ex){
			System.out.println("Bummer");	//用catch块拜放异常状态的处理程序
		}
	}
	
	public static void main(String[] args){
		MusicTest1 mt=new MusicTest1();
		mt.play();
	}
}
