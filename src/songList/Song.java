package songList;

//为了排序的目的  大象只会跟大象比较
class Song implements Comparable<Song>{
	 String title;
	 String artist;
	 String rating;
	 String bpm;
	 
	 // 要比较的对象
	 public int compareTo(Song s) {
		 return title.compareTo(s.getTitle());
	 }
	 
	 Song(String t,String a,String r,String b){
		 title=t;
		 artist=a;
		 rating=r;
		 bpm=b;
	 }
	 
	 public String getTitle(){
		 return title;
	 }
	 public String  getArtist(){
		 return artist;
	 }
	 public String getRating(){
		 return rating;
	 }
	 public String getBpm(){
		 return bpm;
	 }
	 public String toString(){  //将tpString()覆盖  让它返回歌名
		 return title;
	 }


}
