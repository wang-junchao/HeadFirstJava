package DotCom;
import java .io.*;
import java.util.*;
public class GameHelper {
		
	private static final String alphabet="abcdefg";
	private int gridLength=7;
	private int gridSize=49;
	private int [] grid =new int [gridSize];
	private int comCount =0;
	
	public String getUserInput(String prompt){
		String inputLine=null;
		try{
			BufferedReader is =new BufferedReader(
					new InputStreamReader(System.in));
			inputLine =is.readLine();
			if (inputLine.length()==0) return null;
		} catch (IOException e){
			System.out.println("IOException: " +e );
		}
		return inputLine.toLowerCase();
	}
	
	public ArrayList<String> placeDotCom(int comSize){
		ArrayList<String> alphaCells =new ArrayList<String>();
		String [] alphacoords = new String [comSize];	//保存字符串
		String temp=null;	//临时字符串
		int [] coords =new int[comSize];	//现有字符串
		int attempts=0;				//目前测试的字符串
		boolean success=false;		//找到适合位置
		int location =0;			//目前起点
		
		comCount++;				//现在处理到第n个
		int incr=1;				//水平增量
		if((comCount % 2)==1){				//如果是单数号的
			incr=gridLength;
		}
		
		while(!success & attempts++ <200){			//主要搜索循环
			location =(int) (Math.random()*gridSize);		//随机起点
			//System.out.print("try" +location);
			int x=0;
			success=true;
			while (success &&x<comSize){
				if(grid[location]==0){
					coords[x++]=location;
					location +=incr;
					if(location >=gridSize){
						success =false;
					}
					if(x>0 && (location % gridLength == 0)){
						success =false;
					}
				}else{
					success=false;
				}
			}
		}
		
		int x=0;
		int row =0;
		int column =0;
		while (x<comSize){
			grid[coords[x]]=1;
			row=(int)(coords[x] / gridLength);
			column =coords[x] % gridLength;
			temp=String.valueOf(alphabet.charAt(column));
			
			alphaCells.add(temp.concat(Integer .toString(row)));
			x++;
		}
		return alphaCells;
	}
	}
