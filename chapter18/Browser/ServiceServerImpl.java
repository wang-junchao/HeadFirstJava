package Browser;
//远程的实现
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;
//一般的RMI实现
public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer{
	
	HashMap serviceList;	//服务器被存储在HashMap集合中
	
	
	public ServiceServerImpl() throws RemoteException{
		setUpServices();
	}
	private void setUpServices(){
		serviceList=new HashMap();
		serviceList.put("Dice Rolling Service", new DiceService());	//构造函数被调用时会被实际的通用服务初始化
		serviceList.put("Day of the Week Service", new DayOfTheWeekService());
		serviceList.put("Visual Music Service", new MiniMusicService());
	}
	
	public Object[] getServiceList(){
		System.out.println("in remote");
		return serviceList.keySet().toArray();
		//客户端会调用它以取得服务的清单，我们会送出Object的数组，只有HashMap的key，实际服务到用户要求时才通过getService()送出
		
	}
	
	public Service getService(Object serviceKey) throws RemoteException{
		Service theService=(Service) serviceList.get(serviceKey);
		return theService;
		//这个方法会通过key名称来返回HashMap中想对应的服务
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
