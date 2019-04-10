package com.example.demo.thread.future;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 计算start 到 end 累加合
 * @author Administrator
 *
 */
public class ForkJoinTest extends RecursiveTask<Integer>{

	private static AtomicInteger n = new AtomicInteger(0);
	/**任务拆分阈值，值越小，任务拆分的越小*/
	private volatile static int THRESHOLD = 3;
	private int start;
	private int end;
	public ForkJoinTest(int start,int end) {
		this.start=start;
		this.end=end;
	}
	public static void main(String[] args) throws Exception {
		//使用
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTest task = new ForkJoinTest(1, 100);
		Future<Integer> res = pool.submit(task);
		pool.shutdown();
		
		System.out.println("res==="+res.get());
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		//任务足够小就直接计算，否则进行拆分
		if(end -start <= THRESHOLD) {
			for(int i=start;i<=end;i++) {
				sum += i;
			}
			System.out.println(n.get()+"====start:"+start+"   end:"+end + "=="+sum);
		}else {
			//大任务拆分成两个子任务
			int m = (start + end) / 2;
			ForkJoinTest task1 = new ForkJoinTest(start, m);
			ForkJoinTest task2 = new ForkJoinTest(m+1, end);
			System.out.println("第"+n.incrementAndGet()+"次任务拆分start:"+start+"   end:"+end );
			//执行子任务
			task1.fork();
			task2.fork();
			//获取并合并子任务结果
			sum = task1.join() + task2.join();
		}
		return sum;
	}

}
