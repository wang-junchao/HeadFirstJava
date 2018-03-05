package QuizCardBuilder;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
public class QuizCardBuilder {
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private JFrame frame;
	
	public static void main(String[] args){
		QuizCardBuilder builder=new QuizCardBuilder();
	}
	
	public void go(){
		frame=new JFrame("Quiz Card Builder");
		JPanel mainPanel=new JPanel();
		Font bigFont=new Font("sanserif",Font.BOLD,24);
		question=new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		
		JScrollPane qScroller=new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		answer=new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		JScrollPane aScroller=new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton nextButton =new JButton("Next Card");
		
		cardList=new ArrayList<QuizCard>();
		JLabel qLabel=new JLabel("Question:");
		
		JLabel aLabel=new JLabel("Answer:");
		
		//创建菜单，把new与save项目加到File下，然后指定franme使用这个菜单，菜单项目会出发ActionEvent
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		JMenuBar menuBar=new JMenuBar();
		JMenu fileMenu=new JMenu("File");
		JMenuItem newMenuItem=new JMenuItem("New");
		JMenuItem saveMenuItem=new JMenuItem("Save");
		newMenuItem.addActionListener(new NewMenuListener());
		
		saveMenuItem.addActionListener(new SaveMenuListener());
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
	}
	
	public class NextCardListener implements ActionListener{
		public void actionPerformed (ActionEvent ev){
			QuizCard card=new QuizCard(question.getText(),answer.getText());
			cardList.add(card);
			clearCard();
		}
	}
	
	public class SaveMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			QuizCard card=new QuizCard(question.getText(),answer.getText());
			cardList.add(card);
			
			JFileChooser fileSave=new JFileChooser();
			fileSave,showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());	//调出存盘对话框（dialog）等待用户决定 都是考JFileChooser
		}
	}
	
	public class NewMenuListener implements ActionListener{
		public void actionPerformed (ActionEvent ev){
			cardList.clear();
			clearCard();
		}
	}
	private void clearCard(){
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}
	private void saveFile(File file){
		try{
			//将Buffered Writer链接到Filewriter  稍后也会说明
			BufferedWriter writer=new BufferedWriter(new FileWriter(file));
			
			for (QuizCard card:cardList){
				//将ArrayList中的卡片逐个写入到文件中，一行一张卡片。问题和答案用/分开
				writer.write(card.getQuestion()+"/");
				writer.write(card.getAnswer()+"\n");
			}
			writer.close();
		}catch(IOException ex){
			System.out.println("couldn't write the cardList out");
			ex.printStackTrace();
		}
	}
}
