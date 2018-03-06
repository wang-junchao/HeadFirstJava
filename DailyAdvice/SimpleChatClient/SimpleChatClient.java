package SimpleChatClient;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SimpleChatClient {
	JTextArea incoming;
	JTextField outgoing;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	public static void main(String[] args){
		SimpleChatClient client=new SimpleChatClient();
		client.go();
	}
	
	public void go(){
		JFrame frame=new JFrame("Ludicrouly Simple Chat Client");
		JPanel mainPanel=new JPanel();
		incoming=new JTextArea(15,50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller=new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing=new JTextField(20);
		JButton sendButton=new JButton("send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		setUpNetworking();
		//启动新的线程，以内部类作为任务，此任务时读取服务器的socket串流显示在文本区域
		Thread readerThread=new Thread(new IncomingReader());
		readerThread.start();
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		frame.setSize(400, 500);
		frame.setVisible(true);
	}
	
	private	void setUpNetworking(){
		try{
			//使用localhost一边在同一台机器上作测试
			sock=new Socket("127.0.0.1",5000);
			//建立socket和printwriter
			InputStreamReader streamReader=new InputStreamReader(sock.getInputStream());
			reader=new BufferedReader(streamReader);
			writer=new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	//用户按下send按钮时送出文本段内容到服务器上
	public class SendButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			try{
				writer.println(outgoing.getText());	//可以开始写数据，println会把信息送到服务器
				writer.flush();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}
	
	//thread的任务
	//run()会持续读取服务器信息并加到可滚动的文本域上
	public class IncomingReader implements Runnable{
		public void run(){
			String message;
			try{
				while((message=reader.readLine())!=null){
					System.out.println("read"+message);
					incoming.append(message + "\n");
				}
			}catch(Exception ex){ex.printStackTrace();}
		}
	}
}
