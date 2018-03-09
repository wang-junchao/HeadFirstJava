package Browser;
//实现service的通用服务
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
public class DiceService implements Service {
	JLabel label;
	JComboBox numOfDice;
	
	public JPanel getGuiPanel(){	//重要的方法  客户端会调用这个定义在service的方法来创建实际的骰子
		JPanel panel=new JPanel();
		JButton button=new JButton("Roll 'em!'");
		String[] choices={"1","2","3","4","5"};
		numOfDice=new JComboBox(choices);
		label=new JLabel("dice values here");
		button.addActionListener(new RollEmListener());
		panel.add(numOfDice);
		panel.add(button);
		panel.add(label);
		return panel;
	}
	
	public class RollEmListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			//roll the dice
			String diceOutput="";
			String selection=(String)numOfDice.getSelectedItem();
			int numOfDiceToRoll=Integer.parseInt(selection);
			for (int i=0;i<numOfDiceToRoll; i++	){
				int r=(int)((Math.random()*6)+1);
				diceOutput+=(" " +r);
			}
			label.setText(diceOutput);
		}
	}
}
