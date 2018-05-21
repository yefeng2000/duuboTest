package com.example.demo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadJoinTest {
	public static void main(String[] args) throws Exception {
//		countDown();
//		threadJoin();
//		cyclicBarrier();
		exchanger();
	}
	
	private static final Exchanger<String>exgr = new Exchanger<String>();
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
	private static String checkData[] = {"1","B","A","C","D","E"};
	private static String checkData2[] = {"2","B","a","C","d","E"};
	static List<String> rightData = new ArrayList<>();
	static CountDownLatch countDown = new CountDownLatch(2);
	public static void exchanger() throws Exception {
		
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					for(String data:checkData) {
						//String A = "银行流水A";// A录入银行流水数据
						exgr.exchange(data,5,TimeUnit.SECONDS);
					}
					countDown.countDown();
				} catch (Exception e) {
				}
			}
		});
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
//					String B = "银行流水B";// B录入银行流水数据
//					String A = exgr.exchange("B");
//					System.out.println("A和B数据是否一致：" + A.equals(B) + "，A录入的是：" + A + "，B录入是：" + B);
					
					for(String data:checkData2) {
						//String A = "银行流水A";// A录入银行流水数据
						String d;
						d = exgr.exchange(data,5,TimeUnit.SECONDS);
						if(d.equals(data))
							rightData.add(d);
						
					}
					countDown.countDown();
				} catch (Exception e) {
				}
			}
		});
		threadPool.shutdown();
		countDown.await();
		System.out.println(rightData);
	}
	
	static CyclicBarrier cy = new CyclicBarrier(2, new A());
	public static void cyclicBarrier() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(1);
				try {
					cy.await();
				} catch (Exception e) {
				}
				System.out.println(2);
			}
		}).start();
		try {
			cy.await();
		} catch (Exception e) {
		}
		System.out.println(3);
	}

	static class A implements Runnable {
		@Override
		public void run() {
			System.out.println(4);
		}
	}
	
	
	
	//构造器内的数必须与countDown()方法调用次数保持一致，如果参数值>countDown()实际调用次数，那么调用await()方法将会一直等待下去
	static CountDownLatch c = new CountDownLatch(2);

	public static void countDown() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(1);
				c.countDown();
				System.out.println(2);
				c.countDown();
			}
		}).start();
		c.await();
		System.out.println("3");
	}
	
	
	public static void threadJoin() throws Exception {
		Thread parser1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// TimeUnit.SECONDS.sleep(5);
					System.out.println("thread111");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread parser2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//TimeUnit.SECONDS.sleep(5);
					System.out.println("thread222");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		parser1.start();
		parser2.start();
		parser1.join();
		System.out.println("thread111 joined");
		parser2.join();
		System.out.println("all parser finish");
	}
}
