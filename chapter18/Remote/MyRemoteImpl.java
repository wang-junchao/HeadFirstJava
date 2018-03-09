package Remote;
import java.rmi.*;
import java.rmi.server.*;
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {	//创建远程对象的简单方法  必须实现MyRemote的接口
	public String sayHello(){	//得实现出接口所有方法  但注意到你无需声明 RemoteException
		return "Sever says, 'Hey'";
	}
	
	public MyRemoteImpl() throws RemoteException{}	//父类得构造函数声明了异常   所以你必须写出构件函数  因为它代表了你得构件函数会调用有风险得程序代码
	public static void main(String[] args){
		try{
			MyRemote service=new MyRemoteImpl();	//创建出远程对象 然后使用静态Naming.rebind来产生关联   所注册得名称供客户端查询
			Naming.rebind("Remote Hello",service);
		}catch(Exception ex){
			ex.printStackTrace();;
		}
	}
}
