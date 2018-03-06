package DailyAdvice;
import java.io.*;
import java.net.*;


public class DailyAdviceServer {
	String[]	adviceList={"Take smaller bites","Go for the tight jeans. No they do Not"
			+ "make you look fat","One word: inappropriate","Jst for today, be honest.Tell your"
			+ "boss what you *really* think","You might want to rethink that haircut."};
	
	public void go(){
		try{
			//ServerSocket������ͻ��˶���̨������4242�˿��ϵ�Ҫ��
			ServerSocket serverSock=new ServerSocket(4242);
			//��������������ѭ���ȴ�����ͻ��˵�����
			
			while(true){
				//���������ͣ�µȴ�Ҫ�󵽴�֮��Ż����
				Socket sock=serverSock.accept();
				
				//ʹ��Socket�������ͳ��α���Ϣ���ͳ���Ϳ��Խ����ӹر�
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
