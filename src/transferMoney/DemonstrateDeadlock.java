package transferMoney;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class DemonstrateDeadlock {

	private static final int NUM_THREADS = 20;
	private static final int NUM_ACCOUNT = 5;
	private static final int NUM_ITERATIONS = 1000000;
	private static final Object tieLock = new Object();
	
	public static void transferMoney(final Account fromAcct, final Account toAcct, final DollarAmount amount) throws InsufficientFundsException {
		
		class Helper {

			public void transfer() throws InsufficientFundsException {
			
				if (fromAcct.getBalance().compareTo(amount) < 0) {
					throw new InsufficientFundsException();
				}
				else {
					fromAcct.debit(amount);
					toAcct.credit(amount);
				}
			}
		}
		
		int fromHash = System.identityHashCode(fromAcct);
		int toHash = System.identityHashCode(toAcct);
		
		if (fromHash < toHash) {
			synchronized (fromAcct) {
				synchronized (toAcct) {
					new Helper().transfer();
				}
			}
		}
		else if (fromHash > toHash) {
			synchronized(toAcct) {
				synchronized(fromAcct) {
					new Helper().transfer();
				}
			}
		}
		else {
			synchronized(tieLock) {
				synchronized(fromAcct) {
					synchronized(toAcct) {
						new Helper().transfer();
					}
				}
			}
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		
		final Random random = new Random();
		final Account[] accounts = new Account[NUM_ACCOUNT];
		final CountDownLatch endFlag = new CountDownLatch(NUM_THREADS);
		
		for (int i = 0; i < accounts.length; i++) {
			accounts[i] = new Account();
		}
		
		class TransferThread extends Thread {
			
			private CountDownLatch endFlag;
			
			public TransferThread(CountDownLatch endFlag) {
				
				this.endFlag = endFlag;
			}
			
			public void run() {
				
				for (int i = 0; i < NUM_ITERATIONS; i++) {
					int fromAccount = random.nextInt(NUM_ACCOUNT);
					int toAccount = random.nextInt(NUM_ACCOUNT);
					if (fromAccount != toAccount) {
						DollarAmount amount = new DollarAmount(random.nextInt(1000));
						try {
							transferMoney(accounts[fromAccount], accounts[toAccount], amount);
						} catch (InsufficientFundsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				endFlag.countDown();
			}
		}
		
		for (int i = 0; i < NUM_THREADS; i++) {
			new TransferThread(endFlag).start();
		}
		endFlag.await();
		System.out.println(accounts[1].getBalance().getValue());
	}
}
