package DotCom;
import java.util.*;

public class DotCom {
	//DotCOm��ʵ������  ����λ�õ�ArrayList   DotCom������
	private ArrayList<String> locationCells;
	private String name;
	
	//����DotCOmλ�õ�setter����
	public void setLocationCells(ArrayList<String> locs){
		locationCells=locs;
	}
	
	public void setName(String n){
		name=n;
	}
	
	public String checkYourself(String userInput){
		String result="miss";
		int index =locationCells.indexOf(userInput);	//ʹ��indexOf�����������Ҳ��еĻ�����������᷵������λ�ã�û�л᷵��-1
		
		if(index >=0){
			locationCells.remove(index);	//ɾ�������е�Ԫ��
		if(locationCells.isEmpty()){	//�ж��Ƿ����
			result="kill";
			System.out.println("Ouch! you sunk " + name + " : (" );
		}else{
			result="hit";
		}
		}
	return result;
	}
}
