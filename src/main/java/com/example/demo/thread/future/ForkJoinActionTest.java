package com.example.demo.thread.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkJoinActionTest extends RecursiveAction {
	private static AtomicInteger n = new AtomicInteger(0);
	/**任务拆分阈值，值越小，任务拆分的越小*/
	private volatile static int THRESHOLD = 5;
	private List<String> datas;
	List<String> rest = new ArrayList<>();
	public ForkJoinActionTest(List<String> datas) {
		this.datas = datas;
	}
	
	
	@Override
	protected void compute() {
		if(datas == null)
			return;
		if(datas.size() <= THRESHOLD) {
			//直接处理
			for(String d:datas) {
				System.out.println(d);
				rest.add(d);
				
			}
			//System.out.println(n.get()+"==="+rest+"==datas:"+datas);
		}else {//进行任务拆分
			int middleSize = datas.size()/2;
			List<String> leftData = datas.subList(0, middleSize);
			List<String> rightData = datas.subList(middleSize, datas.size());
			
			ForkJoinActionTest leftAction = new ForkJoinActionTest(leftData);
			ForkJoinActionTest rightAction = new ForkJoinActionTest(rightData);
			//System.out.println("第"+n.incrementAndGet()+"次任务拆分leftData:"+leftData+"   rightData:"+rightData );
			//执行两个子任务
			leftAction.fork();
			rightAction.fork();
			
			leftAction.join();
			rightAction.join();
		}
	}
	
	public static void main(String[] args) {

		try {
			List<String> datas = new ArrayList<>();
			datas.add("1");datas.add("2");datas.add("3");datas.add("4");datas.add("5");	datas.add("6");datas.add("7");
			datas.add("8");datas.add("9");datas.add("10");datas.add("11");datas.add("12");datas.add("13");
			datas.add("14");datas.add("15");datas.add("16");datas.add("17");datas.add("18");datas.add("19");
			datas.add("20");datas.add("21");datas.add("22");datas.add("23");datas.add("24");datas.add("25");
			datas.add("26");datas.add("27");datas.add("28");datas.add("29");datas.add("30");datas.add("31");
			datas.add("32");datas.add("33");datas.add("34");datas.add("35");datas.add("36");datas.add("37");
			datas.add("38");datas.add("39");datas.add("40");datas.add("41");datas.add("42");datas.add("43");
			datas.add("44");datas.add("45");datas.add("46");datas.add("47");datas.add("48");datas.add("49");
			 
	        ForkJoinPool forkJoinPool =new ForkJoinPool();//创建他的线程池
	 
	        // 提交可分解的ForkJoinTask任务
	        Future ft = forkJoinPool.submit(new ForkJoinActionTest(datas));
	 
	        Object obj = ft.get();
	 
	        System.out.println(obj+"==查询的结果个数是："+datas.size());
	 
	        forkJoinPool.shutdown();//关闭线程池
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
