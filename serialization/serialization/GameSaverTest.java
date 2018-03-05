package serialization;
import java.io.*;
public class GameSaverTest {
	public static void main(String[] args){
		//创建人物
		GameCharacter one=new GameCharacter(50,"Elf",new String[]{"bow","sword","dust"});
		GameCharacter two=new GameCharacter(200,"Troll",new String[] {"bare hands","big ax"});
		GameCharacter three=new GameCharacter(120,"Magician",new String[] {"spells","invisibility"});
		
		//假设此处有改变人物状态值的程序代码
		
		try{
			ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream("Game.ser"));
			os.writeObject(one);
			os.writeObject(two);
			os.writeObject(three);
			os.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		//设定为null，因此无法存取堆上的这些对象
		one =null;
		two=null;
		three=null;
				
		try{
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("Game.ser"));
			GameCharacter oneRestore=(GameCharacter) is.readObject();
			GameCharacter twoRestore=(GameCharacter) is.readObject();
			GameCharacter threeRestore=(GameCharacter) is.readObject();
			
			System.out.println("one's type: "+ oneRestore.getType());	//看结果
			System.out.println("Two's type: "+ twoRestore.getType());
			System.out.println("Three's type: "+ threeRestore.getType());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
