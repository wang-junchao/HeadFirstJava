package Browser;
//�û���
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
		
		Object[] services=getServicesList();	//�˷���ִ��RMI registy��ѯ ȡ��stub������getServiceList()
		
		serviceList=new JComboBox(services);	//�Ѵ˷�����ӵ�JComboBox����  JComboBox֪�������ʾ�����е��ַ���
		
		frame.getContentPane().add(BorderLayout.NORTH, serviceList);
		
		serviceList.addActionListener(new MyListListener());
		
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	void loadService(Object serviceSelection){
		try{
			Service svc=server.getService(serviceSelection);
			
			//��ʵ�ʵķ�����ӵ�GUI��mainPanel��
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
			//ִ��RMI��ѯ ���stub
			obj =Naming.lookup("rmi://127.0.0.1/ServiceServer");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		//��stubת����remote interface������  ��˲��ܵ�������setServiceList()
		server=(ServiceServer) obj;
		
		try{
			services=server.getServiceList();	//�᷵��object������
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return services;
	}
	
	class MyListListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object selection=serviceList.getSelectedItem();
			loadService(selection);
			//�û����JComboBox����Ŀ��ᵽ������ ��˾ͻ����Ӧ�ķ������
		}
	}
	
	public static void main(String[] args){
		new ServiceBrowser().buildGUI();
	}
}
