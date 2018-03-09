package Browser;
//用户端
import java.awt.*;
import javax.swing.*;
import java.rmi.*;
import java.awt.event.*;
public class ServiceBrowser {
	JPanel mainPanel;
	JComboBox serviceList;
	ServiceServer server;
	
	public void buildGUI(){
		JFrame frame=new JFrame("RMI browser");
		mainPanel=new JPanel();
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		
		Object[] services=getServicesList();	//此方法执行RMI registy查询 取得stub并调用getServiceList()
		
		serviceList=new JComboBox(services);	//把此服务添加到JComboBox里面  JComboBox知道如何显示数组中的字符串
		
		frame.getContentPane().add(BorderLayout.NORTH, serviceList);
		
		serviceList.addActionListener(new MyListListener());
		
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	void loadService(Object serviceSelection){
		try{
			Service svc=server.getService(serviceSelection);
			
			//把实际的服务添加到GUI的mainPanel中
			mainPanel.removeAll();
			mainPanel.add(svc.getGuiPanel());
			mainPanel.validate();
			mainPanel.repaint();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	Object[] getServicesList(){
		Object obj=null;
		Object[] services=null;
		
		try{
			//执行RMI查询 获得stub
			obj =Naming.lookup("rmi://127.0.0.1/ServiceServer");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		//将stub转换成remote interface的类型  如此才能调用它的setServiceList()
		server=(ServiceServer) obj;
		
		try{
			services=server.getServiceList();	//会返回object的数组
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return services;
	}
	
	class MyListListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object selection=serviceList.getSelectedItem();
			loadService(selection);
			//用户点击JComboBox的项目后会到这里来 因此就会把相应的服务加载
		}
	}
	
	public static void main(String[] args){
		new ServiceBrowser().buildGUI();
	}
}
