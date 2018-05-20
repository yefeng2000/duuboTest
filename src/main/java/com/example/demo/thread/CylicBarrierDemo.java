package com.example.demo.thread;

import java.util.Map.Entry;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CylicBarrierDemo implements Runnable {

	/**
	* 创建4个屏障，处理完之后执行当前类的run方法
	*/
	private static CyclicBarrier c = null;
	/**
	* 假设只有4个sheet，所以只启动4个线程
	*/
	private ExecutorService executor = Executors.newFixedThreadPool(4);
	/**
	* 保存每个sheet计算出的银流结果
	*/
	private ConcurrentHashMap <String, Integer> sheetBankWaterCount = new ConcurrentHashMap<String, Integer>();
	
	private void count() {
		for (int i = 0; i < 4; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					// 计算当前sheet的银流数据，计算代码省略
					sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
					System.out.println("wait before:"+Thread.currentThread().getName());
					// 银流计算完成，插入一个屏障
					try {
						c.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
					System.out.println("wait after:"+Thread.currentThread().getName());
				}
			});
		}
		executor.shutdown();
	}
	
	@Override
	public void run() {
		int result = 0;
		// 汇总每个sheet计算出的结果
		for (Entry<String, Integer>sheet : sheetBankWaterCount.entrySet()) {
			result += sheet.getValue();
		}
		// 将结果输出
		sheetBankWaterCount.put("result", result);
		System.out.println("result:"+result);
	}
	public static void main(String[] args) throws Exception {
		CylicBarrierDemo demo = new CylicBarrierDemo();
		c = new CyclicBarrier(4,demo);
		System.out.println("======begin=======");
		demo.count();
		System.out.println("======end=======");
		//TimeUnit.SECONDS.sleep(20);
		System.out.println("before:"+c.getNumberWaiting());
		
		System.out.println("getNumberWaiting:"+c.getNumberWaiting());
		
		System.out.println("after:"+c.getNumberWaiting());
	}

}
