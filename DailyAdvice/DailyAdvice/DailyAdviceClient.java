package DailyAdvice;
import java.io.*;
import java.net.*;		//socket

public class DailyAdviceClient {
	public void go(){
		try{
			Socket s=new Socket("127.0.0.1",4242);		//对端口4242作连接
			
			InputStreamReader streamReader=new InputStreamReader(s.getInputStream());
			BufferedReader reader=new BufferedReader(streamReader);		//链接数据串流
			
			String advice=reader.readLine();
			System.out.println("Today you should"+advice);
			
			reader.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public static void main(String[] args){
		DailyAdviceClient client=new DailyAdviceClient();
		client.go();
	}
}
