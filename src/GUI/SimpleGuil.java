package GUI;
import javax.swing.*;
public class SimpleGuil {
	public static void main(String[] args){
		
		JFrame frame =new JFrame();	//创建frame和button
		JButton button=new JButton("click me");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//这一行程序会在window关闭时把程序结束掉
		
		frame.getContentPane().add(button);	//把button加到frame的pane上
		
		frame.setSize(300, 300);  	//设定frame的大小
		
		frame.setVisible(true);   //最后把frame显示出来
	}
}
