package Browser;
//Զ�̵�ʵ��
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;
//һ���RMIʵ��
public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer{
	
	HashMap serviceList;	//���������洢��HashMap������
	
	
	public ServiceServerImpl() throws RemoteException{
		setUpServices();
	}
	private void setUpServices(){
		serviceList=new HashMap();
		serviceList.put("Dice Rolling Service", new DiceService());	//���캯��������ʱ�ᱻʵ�ʵ�ͨ�÷����ʼ��
		serviceList.put("Day of the Week Service", new DayOfTheWeekService());
		serviceList.put("Visual Music Service", new MiniMusicService());
	}
	
	public Object[] getServiceList(){
		System.out.println("in remote");
		return serviceList.keySet().toArray();
		//�ͻ��˻��������ȡ�÷�����嵥�����ǻ��ͳ�Object�����飬ֻ��HashMap��key��ʵ�ʷ����û�Ҫ��ʱ��ͨ��getService()�ͳ�
		
	}
	
	public Service getService(Object serviceKey) throws RemoteException{
		Service theService=(Service) serviceList.get(serviceKey);
		return theService;
		//���������ͨ��key����������HashMap�����Ӧ�ķ���
	}
	
	public static void main(String[] args){
		try{
			Naming.rebind("ServiceServer", new ServiceServerImpl());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("Remote service is running");
	}
}
