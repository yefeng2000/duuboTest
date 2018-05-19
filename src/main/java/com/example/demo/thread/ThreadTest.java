package com.example.demo.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

	private Lock lock = new ReentrantLock();
	private Condition addCon = lock.newCondition();
	private Condition subCon = lock.newCondition();
	private static int n = 0;
	public static void main(String[] args) {
		ThreadTest test = new ThreadTest();
		test.addThread();
		test.subThread();
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void addThread() {
		for(int i=0;i<20;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					add();
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
