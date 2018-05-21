package com.example.demo.thread.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
/**
 * 非耗时性任务建议不要使用forkjoin进行任务拆分
 * @author cksoft
 *
 */
public class ForkJoinDemo extends RecursiveTask<Long>{

	/**任务拆分阈值，值越小，任务拆分的越小*/
	private volatile static int THRESHOLD = 10000;
	private final static int MAX_DATA = 10000000;
	private int start;
	private int end;
	private List<Integer> datas;
	public ForkJoinDemo(int start,int end,List<Integer> datas) {
		this.start=start;
		this.end=end;
		this.datas=datas;
	}
	public static void main(String[] args) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				getRes(datas());
			}
		}).start();
		ForkJoinRes();
	}
	
	/**
	 * 使用forkjoin计算任务结果及耗时
	 * @return
	 * @throws Exception
	 */
	public static long ForkJoinRes() throws Exception {
		long start = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinDemo task = new ForkJoinDemo(0, datas().size(), datas());
		ForkJoinTask<Long> res = pool.submit(task);
		pool.shutdown();
		long r = res.get();
		System.out.println("res:"+r+"===forkjoin==time:"+(System.currentTimeMillis()-start));
		return r;
	}
	
	/**
	 * 使用传统方式计算结果及耗时
	 * @param dts
	 * @return
	 */
	public static long getRes(List<Integer> dts) {
		long start = System.currentTimeMillis();
		long res = 0;
		for(Integer i:dts) {
			res += i;
		}
		System.out.println("res:"+res+"===for Each==time:"+(System.currentTimeMillis()-start));
		return res;
	}
	
	public static List<Integer> datas(){
		List<Integer> dts = new ArrayList<>();
		for(int i=1;i<=MAX_DATA;i++) {
			dts.add(i);
		}
		return dts;
	}
	


	@Override
	protected Long compute() {
		long res = 0;
		if(end - start <= THRESHOLD) {
			// 对list数据拆分成若干个子任务计算
			for(int i=start;i<end;i++) {
				res += datas.get(i);
				//System.out.println(i+"=="+datas.get(i));
			}
		}else {
			int m = (start + end)/2;
			ForkJoinDemo demo = new ForkJoinDemo(start, m, datas);
			ForkJoinDemo demo2 = new ForkJoinDemo(m, end, datas);
			demo.fork();
			demo2.fork();
			res = demo.join() + demo2.join();
		}
		return res;
	}

}
