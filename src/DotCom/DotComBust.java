package DotCom;
import java.util.*;

public class DotComBust {
	//声明并初始化变量
	private GameHelper helper=new GameHelper();
	private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
	private int numOfGuesses=0;
	
	private void setUpGame(){
		//创建3各DotCom对象并指派名称并植入ArrayList
		DotCom one=new DotCom();
		one.setName("Pets.com");
		DotCom two=new DotCom();
		two.setName("eToys.com");
		DotCom three=new DotCom();
		three.setName("Go2.com");
		dotComsList.add(one);
		dotComsList.add(two);
		dotComsList.add(three);
		
		//列出简短的提示
		System.out.println("Your goal is to sin three dot coms.");
		System.out.println("Pets.com,eToys.com,Go2.com");
		System.out.println("Try to sink them all in the fewest number of guesses");
		
		for (DotCom dotComToSet : dotComsList){	//对List中所有的DotCom
			ArrayList<String> newLocation =helper.placeDotCom(3);	//要求DotCom的位置
			
			dotComToSet.setLocationCells(newLocation);	//调用这个DotCom的setter方法来指派刚取得的位置
		}
	}
	
	private void startPlaying(){
		
		while(!dotComsList.isEmpty()){	//判断DotCom的List是否为空
			String userGuess=helper.getUserInput("Enter a guess");	//取得玩家的输入
			checkUserGuess(userGuess);	//调用checkUserGuess方法
		}
		finishGame();
	}
	
	private void checkUserGuess(String userGuess){
		
		numOfGuesses++;	//递增玩家猜测的次数计数
		String result="miss";	//先假设没有名字
		
		for (DotCom dotComToTest : dotComsList){	//对list中所有的DotCom重复
			result =dotComToTest.checkYourself(userGuess);	//要求DotCom检查是否命中或击沉
			
			if(result.equals("hit")){
				break;	//提前跳出循环
			}
			if(result.equals("kill")){
				dotComsList.remove(dotComToTest);
				break;
			}
		}
		
		System.out.println(result); 	//列出结果
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
