package DotCom;
import java.util.*;

public class DotCom {
	//DotCOm的实例变量  保存位置的ArrayList   DotCom的名称
	private ArrayList<String> locationCells;
	private String name;
	
	//更新DotCOm位置的setter方法
	public void setLocationCells(ArrayList<String> locs){
		locationCells=locs;
	}
	
	public void setName(String n){
		name=n;
	}
	
	public String checkYourself(String userInput){
		String result="miss";
		int index =locationCells.indexOf(userInput);	//使用indexOf方法，如果玩家猜中的话，这个方法会返回它的位置，没有会返回-1
		
		if(index >=0){
			locationCells.remove(index);	//删除被猜中的元素
		if(locationCells.isEmpty()){	//判断是否击沉
			result="kill";
			System.out.println("Ouch! you sunk " + name + " : (" );
		}else{
			result="hit";
		}
		}
	return result;
	}
}
