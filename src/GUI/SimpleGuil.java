package GUI;
import javax.swing.*;
public class SimpleGuil {
	public static void main(String[] args){
		
		JFrame frame =new JFrame();	//����frame��button
		JButton button=new JButton("click me");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//��һ�г������window�ر�ʱ�ѳ��������
		
		frame.getContentPane().add(button);	//��button�ӵ�frame��pane��
		
		frame.setSize(300, 300);  	//�趨frame�Ĵ�С
		
		frame.setVisible(true);   //����frame��ʾ����
	}
}
