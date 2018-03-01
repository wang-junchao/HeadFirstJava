package DotCom;

public class simpleDotCom {
	public static void main (String[] args){
		SimpleDotCom dot=new SimpleDotCom();  //初始化一个simpleDotCom对象
		
		int[] locations={2,3,4};//创建带有dot com 位置的数组
		dot.setLocationCells(locations); //调用dot com 的setter
		
		String userGuess="2";   //假的猜测
		String result=dot.checkYourself(userGuess);   //调用被侧方法并传入假的数据
		String testResult="failed";
		if(result.equals("hit")){
			testResult="passed";  //测试应该要返回 hit才算成功
		}
		System.out.println(testResult);
		
		
	}
}
