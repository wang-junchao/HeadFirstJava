package DotCom;

public class simpleDotComTestDrive {
	public static void main (String[] args){
		SimpleDotCom dot=new SimpleDotCom();  //��ʼ��һ��simpleDotCom����
		
		int[] locations={2,3,4};//��������dot com λ�õ�����
		dot.setLocationCells(locations); //����dot com ��setter
		
		String userGuess="2";   //�ٵĲ²�
		String result =dot.checkYourself(userGuess);   //���ñ��෽��������ٵ�����
	}
}
