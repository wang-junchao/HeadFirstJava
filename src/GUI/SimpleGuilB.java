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
		JFrame frame =new JFrame();	//创建frame和button
		button=new JButton("click me");
		
		button.addActionListener(this);
		
		frame.getContentPane().add(button);	//把button加到frame的pane上
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//这一行程序会在window关闭时把程序结束掉
		frame.setSize(300, 300);  	//设定frame的大小
		frame.setVisible(true);   //最后把frame显示出来
	}
	public void actionPerformed(ActionEvent event){
		button.setText("I've been clicked!");
	}
}
