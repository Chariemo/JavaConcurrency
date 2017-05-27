package basic;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.sun.java_cup.internal.runtime.Scanner;

public class CopyOnWriteDemo {

	static class modifierA implements Runnable {

		private final CopyOnWriteArrayList<Integer> COWAList;
		
		public modifierA(CopyOnWriteArrayList<Integer> COWAList) {
			
			this.COWAList = COWAList;
		}
		
		@Override
		public void run() {
			
			try {
				while (!Thread.interrupted()) {
					System.out.println("Change 111 started");
					COWAList.set(0, 111);
					System.out.println("After change 111");
					TimeUnit.MILLISECONDS.sleep(100);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	static class modifierB implements Runnable {

		private final CopyOnWriteArrayList<Integer> COWAList;
		
		public modifierB(CopyOnWriteArrayList<Integer> COWAList) {
			
			this.COWAList = COWAList;
		}
		
		@Override
		public void run() {
			
			try {
				while (!Thread.interrupted()) {
					System.out.println("Change 222 started");
					COWAList.set(0, 222);
					System.out.println("After change 222");
					TimeUnit.MILLISECONDS.sleep(100);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	static class GetNode implements Runnable {

		private final CopyOnWriteArrayList<Integer> COWAList;
		
		public GetNode(CopyOnWriteArrayList<Integer> COWAList) {
			
			this.COWAList = COWAList;
		}
		
		@Override
		public void run() {
			
			try {
				while (!Thread.interrupted()) {
					for (int node : COWAList) {
						System.out.println(node);
					}
					TimeUnit.MILLISECONDS.sleep(100);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
//		CopyOnWriteArrayList<Integer> COWAList = new CopyOnWriteArrayList<>();
//		COWAList.add(000);
//		ExecutorService executor = Executors.newCachedThreadPool();
//		executor.execute(new GetNode(COWAList));
//		executor.execute(new modifierA(COWAList));
//		executor.execute(new modifierB(COWAList));
//		TimeUnit.SECONDS.sleep(5);
//		executor.shutdownNow();
		
		java.util.Scanner input = new java.util.Scanner(System.in);

	}
	
	static boolean isPrime(int x) {
		
		boolean result = true;
		
		int mid = (int)Math.sqrt(x);
		for (int i = 2; i <= mid; i++) {
			if (x % i == 0) {
				result = false;
				break;
			}
		}
		return result;
	}
}
