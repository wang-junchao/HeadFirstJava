package GUI;
import javax.swing.*;
import java.awt.event.*;
public class SimpleGuilB implements ActionListener {
	JButton button;
	
	public static void main(String[] args){
		SimpleGuilB gui=new SimpleGuilB();
		gui.go();
		
	}
	public void go(){
		JFrame frame =new JFrame();	//����frame��button
		button=new JButton("click me");
		
		button.addActionListener(this);
		
		frame.getContentPane().add(button);	//��button�ӵ�frame��pane��
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//��һ�г������window�ر�ʱ�ѳ��������
		frame.setSize(300, 300);  	//�趨frame�Ĵ�С
		frame.setVisible(true);   //����frame��ʾ����
	}
	public void actionPerformed(ActionEvent event){
		button.setText("I've been clicked!");
	}
}
