package MIDIMusic;
import javax.sound.midi.*;	//���øð�

public class MusicTest1 {
	public void play(){
		try{
			//���������ǽ�MiDi��Ϣ��ϳ�����
			Sequencer sequencer =MidiSystem.getSequencer();	//���з��յĳ������try��
			System.out.println("We got a sequencer");
		}catch(MidiUnavailableException ex){
			System.out.println("Bummer");	//��catch��ݷ��쳣״̬�Ĵ������
		}
	}
	
	public static void main(String[] args){
		MusicTest1 mt=new MusicTest1();
		mt.play();
	}
}
