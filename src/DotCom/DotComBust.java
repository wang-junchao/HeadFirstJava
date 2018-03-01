package DotCom;
import java.util.*;

public class DotComBust {
	//��������ʼ������
	private GameHelper helper=new GameHelper();
	private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
	private int numOfGuesses=0;
	
	private void setUpGame(){
		//����3��DotCom����ָ�����Ʋ�ֲ��ArrayList
		DotCom one=new DotCom();
		one.setName("Pets.com");
		DotCom two=new DotCom();
		two.setName("eToys.com");
		DotCom three=new DotCom();
		three.setName("Go2.com");
		dotComsList.add(one);
		dotComsList.add(two);
		dotComsList.add(three);
		
		//�г���̵���ʾ
		System.out.println("Your goal is to sin three dot coms.");
		System.out.println("Pets.com,eToys.com,Go2.com");
		System.out.println("Try to sink them all in the fewest number of guesses");
		
		for (DotCom dotComToSet : dotComsList){	//��List�����е�DotCom
			ArrayList<String> newLocation =helper.placeDotCom(3);	//Ҫ��DotCom��λ��
			
			dotComToSet.setLocationCells(newLocation);	//�������DotCom��setter������ָ�ɸ�ȡ�õ�λ��
		}
	}
	
	private void startPlaying(){
		
		while(!dotComsList.isEmpty()){	//�ж�DotCom��List�Ƿ�Ϊ��
			String userGuess=helper.getUserInput("Enter a guess");	//ȡ����ҵ�����
			checkUserGuess(userGuess);	//����checkUserGuess����
		}
		finishGame();
	}
	
	private void checkUserGuess(String userGuess){
		
		numOfGuesses++;	//������Ҳ²�Ĵ�������
		String result="miss";	//�ȼ���û������
		
		for (DotCom dotComToTest : dotComsList){	//��list�����е�DotCom�ظ�
			result =dotComToTest.checkYourself(userGuess);	//Ҫ��DotCom����Ƿ����л����
			
			if(result.equals("hit")){
				break;	//��ǰ����ѭ��
			}
			if(result.equals("kill")){
				dotComsList.remove(dotComToTest);
				break;
			}
		}
		
		System.out.println(result); 	//�г����
	}
	
	private void finishGame(){
		System.out.println("All Dot COms are dead! Your stock is now worthless.");
		if(numOfGuesses<=18){
			System.out.println("It only took you " + numOfGuesses + " guesses.");
			System.out.println("You got out before your option sank.");
		}else{
			System.out.println("Took you long enough." + numOfGuesses +"guesses.");
			System.out.println("Fish are dancing with your options");
		}
	}
	
	public static void main (String[] args){
		DotComBust game=new DotComBust();
		game.setUpGame();
		game.startPlaying();
	}
}
