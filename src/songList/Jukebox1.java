package songList;
import java.util.*;
import java.io.*;
public class Jukebox1 {
	ArrayList<Song> songList= new ArrayList<Song>();	//歌曲名称会存在String的ArrayLsit上
	public static void main (String[] args){
		new Jukebox1().go();
	}
	
	public void go(){		//方法会载入文件并列出内容
		getSongs();
		System.out.println(songList);
		Collections.sort(songList);
		System.out.println(songList);
	}
	
	//读取文件的程序
	void getSongs(){
		try{
			File file=new File("E:/eclipse/Head first java/src/SongList.txt");
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line=null;
			while((line=reader.readLine())!=null){
				addSong(line);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	void addSong(String lineToParse){
		String[] tokens=lineToParse.split("/");	//split() 方法会用反斜线来拆开歌曲内容
		Song nextSong=new Song(tokens[0],tokens[1],tokens[2],tokens[3]);	//因为只需要歌名，所以只取第一项加入songList
		songList.add(nextSong);
	}
}
