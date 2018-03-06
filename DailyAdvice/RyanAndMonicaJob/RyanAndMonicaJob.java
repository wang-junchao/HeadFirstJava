package RyanAndMonicaJob;

class BankAccount {
	private int balance=100;
	
	public int getBalance(){
		return balance;
	}
	public void withdraw(int amount){
		balance =balance-amount;
	}
	
}


public class RyanAndMonicaJob implements Runnable{
	//只有一个实例代表只有一个共享的账户
	private BankAccount account =new BankAccount();
	
	public static void main(String[] args){
		RyanAndMonicaJob theJob=new RyanAndMonicaJob();	//任务初始化
		//创建使用相同任务的两个线程，代表两个线程都会存取一个账户
		Thread one=new Thread(theJob);
		Thread two=new Thread(theJob);
		one.setName("Ryan");
		two.setName("Monica");
		one.start();
		two.start();
	}
	
	public void run(){
		//检查账户余额，如果透支就列出信息  不然就睡会，醒来完成提款操作
		for (int x=0;x<10;x++){
			makeWithdrawal(10);
			if(account.getBalance()<0){
				System.out.println("Overdraw!");
			}
		}
	}
	
	private void makeWithdrawal(int amount){
		if(account.getBalance()>=amount){
			System.out.println(Thread.currentThread().getName()+ " is about to withdraw");
			try{
				System.out.println(Thread.currentThread().getName()+ " is going to sleep");
				Thread.sleep(500);
			}catch(InterruptedException ex){ex.printStackTrace();}
			System.out.println(Thread.currentThread().getName()+ " woke up.");
			account.withdraw(amount);
			System.out.println(Thread.currentThread().getName()+ " completes the withdrawl");
			}
		else{
			System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
		}	
	}
}
