package Remote;
import java.rmi.*;
import java.rmi.server.*;
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {	//����Զ�̶���ļ򵥷���  ����ʵ��MyRemote�Ľӿ�
	public String sayHello(){	//��ʵ�ֳ��ӿ����з���  ��ע�⵽���������� RemoteException
		return "Sever says, 'Hey'";
	}
	
	public MyRemoteImpl() throws RemoteException{}	//����ù��캯���������쳣   ���������д����������  ��Ϊ����������ù�������������з��յó������
	public static void main(String[] args){
		try{
			MyRemote service=new MyRemoteImpl();	//������Զ�̶��� Ȼ��ʹ�þ�̬Naming.rebind����������   ��ע������ƹ��ͻ��˲�ѯ
			Naming.rebind("Remote Hello",service);
		}catch(Exception ex){
			ex.printStackTrace();;
		}
	}
}
