package DotCom;

public class SimpleDotComGame {
	public static void main(String[] args){
		int numOfGuesses=0;	//��¼��Ҳ²�����ı���
		GameHelper helper=new GameHelper();	//���ǻ�д���������ȡ����ҵ����룬�����ȼ�װ����java�ṩ��
		
		SimpleDotCom theDotCom =new SimpleDotCom();	//����dotcom����
		int randomNum=(int) (Math.random() * 5);	//�������������һ����λ�ã�Ȼ���Դ�����������
		
		int[] locations ={randomNum,randomNum+1,randomNum+2};
		theDotCom.setLocationCells(locations);	//��ֵλ��
		boolean isAlive=true;	//��������¼��Ϸ�Ƿ������boolean����������whileѭ����
		
		while(isAlive==true){
			String guess=helper.getUserInput("enter a number");	//ȡ�����������ַ���
			String result=theDotCom.checkYourself(guess);	//�����ҵĲ²Ⲣ������洢��String��
			numOfGuesses++;
			if(result.equals("kill")){	//�Ƿ�����������������趨isAliveΪfalse��ӡ���²����
				isAlive=false;
				System.out.println("You took " + numOfGuesses + " guesses");
			}
		}
	}
}
