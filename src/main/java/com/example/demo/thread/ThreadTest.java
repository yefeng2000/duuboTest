package com.example.demo.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

	private Lock lock = new ReentrantLock();
	private Condition addCon = lock.newCondition();
	private Condition subCon = lock.newCondition();
	private static int n = 0;
	private static ThreadLocal<String> threadLocal;
	
	public static void main(String[] args) {
		ThreadTest test = new ThreadTest();
		test.addThread();
		test.subThread();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if((threadLocal != null))
		System.out.println(threadLocal.get());
		System.out.println("final n====="+n);
	}
	
	public void add() {
		try {
			lock.lock();
			while(n>9) {
				addCon.await();
			}
			n++;
			System.out.println("add n====="+n);
			subCon.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	public void sub() {
		try {
			lock.lock();
			while(n<=0) {
				subCon.await();
			}
			n--;
			System.out.println("-----sub n====="+n);
			addCon.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	static int  k;
	public void addThread() {
		for(int i=0;i<20;i++) {
			k = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					add();
					if(k == 18)
					threadLocal = new ThreadLocal<String>() {
						@Override
						protected String initialValue() {
							return Thread.currentThread().getName();
						}
						
					};
				}
			}).start();
		}
	}
	
	public void subThread() {
		for(int i=0;i<20;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					sub();
				}
			}).start();
		}
	}

}
