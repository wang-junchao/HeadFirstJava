package DailyAdvice;
import java.io.*;
import java.net.*;


public class DailyAdviceServer {
	String[]	adviceList={"Take smaller bites","Go for the tight jeans. No they do Not"
			+ "make you look fat","One word: inappropriate","Jst for today, be honest.Tell your"
			+ "boss what you *really* think","You might want to rethink that haircut."};
	
	public void go(){
		try{
			//ServerSocket会监听客户端对这台机器在4242端口上的要求
			ServerSocket serverSock=new ServerSocket(4242);
			//服务器进入无穷循环等待服务客户端的请求
			
			while(true){
				//这个方法会停下等待要求到达之后才会继续
				Socket sock=serverSock.accept();
				
				//使用Socket连接来送出嘉宾信息，送出后就可以将连接关闭
				PrintWriter writer=new PrintWriter(sock.getOutputStream());
				String advice=getAdvice();
				writer.println(advice);
				writer.close();
				System.out.println(advice);
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	private String getAdvice(){
		int random=(int)(Math.random()*adviceList.length);
		return adviceList[random];
	}
	
	public static void main(String[] arsg){
		DailyAdviceServer server=new DailyAdviceServer();
		server.go();
	}
}
