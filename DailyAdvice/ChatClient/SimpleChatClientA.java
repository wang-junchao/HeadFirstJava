package ChatClient;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SimpleChatClientA {
	JTextField outgoing;
	PrintWriter writer;
	Socket sock;
	
	public void go(){
		JFrame frame=new JFrame("Ludicrouly Simple Chat Client");
		JPanel mainPanel=new JPanel();
		outgoing=new JTextField(20);
		JButton sendButton=new JButton("send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		setUpNetworking();
		frame.setSize(400, 500);
		frame.setVisible(true);
	}
	
	private	void setUpNetworking(){
		try{
			//使用localhost一边在同一台机器上作测试
			sock=new Socket("127.0.0.1",5000);
			//建立socket和printwriter
			writer=new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
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
	public static void main(String[] args){
		new SimpleChatClientA().go();
	}
}
