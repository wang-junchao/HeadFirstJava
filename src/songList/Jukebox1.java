package songList;
import java.util.*;
import java.io.*;
public class Jukebox1 {
	ArrayList<Song> songList= new ArrayList<Song>();	//�������ƻ����String��ArrayLsit��
	public static void main (String[] args){
		new Jukebox1().go();
	}
	
	public void go(){		//�����������ļ����г�����
		getSongs();
		System.out.println(songList);
		Collections.sort(songList);
		System.out.println(songList);
	}
	
	//��ȡ�ļ��ĳ���
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
		String[] tokens=lineToParse.split("/");	//split() �������÷�б�����𿪸�������
		Song nextSong=new Song(tokens[0],tokens[1],tokens[2],tokens[3]);	//��Ϊֻ��Ҫ����������ֻȡ��һ�����songList
		songList.add(nextSong);
	}
}
