package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.collections.map.HashedMap;

public class RandomUtil {

    

    /** 

     * 这是典型的随机洗牌算法。 

     * 流程是从备选数组中选择一个放入目标数组中，将选取的数组从备选数组移除（放至最后，并缩小选择区域） 

     * 算法时间复杂度O(n) 

     * @return 随机8为不重复数组 

     */  

    public static String generateNumber() {  

        String no="";  

        //初始化备选数组  

        int[] defaultNums = new int[10];  

        for (int i = 0; i < defaultNums.length; i++) {  

            defaultNums[i] = i;  

        }  

  

        Random random = new Random();  

        int[] nums = new int[LENGTH];  

        //默认数组中可以选择的部分长度  

        int canBeUsed = 10;  

        //填充目标数组  

        for (int i = 0; i < nums.length; i++) {  

            //将随机选取的数字存入目标数组  

            int index = random.nextInt(canBeUsed);  

            nums[i] = defaultNums[index];  

            //将已用过的数字扔到备选数组最后，并减小可选区域  

            swap(index, canBeUsed - 1, defaultNums);  

            canBeUsed--;  

        }  

        if (nums.length>0) {  

            for (int i = 0; i < nums.length; i++) {  

                no+=nums[i];  

            }  

        }  

  

        return no;  

    }  

    private static final int LENGTH = 8;  

  

    private static void swap(int i, int j, int[] nums) {  

        int temp = nums[i];  

        nums[i] = nums[j];  

        nums[j] = temp;  

    }  

      

    public static String generateNumber2() {  

        String no="";  

        int num[]=new int[8];  

        int c=0;  

        for (int i = 0; i < 8; i++) {  

            num[i] = new Random().nextInt(10);  

            c = num[i];  

            for (int j = 0; j < i; j++) {  

                if (num[j] == c) {  

                    i--;  

                    break;  

                }  

            }  

        }  

        if (num.length>0) {  

            for (int i = 0; i < num.length; i++) {  

                no+=num[i];  

            }  

        }  

        return no;  

    }  

    public static String getStr() {
    	StringBuilder str=new StringBuilder();//定义变长字符串
    	Random random=new Random();
    	//随机生成数字，并添加到字符串
    	for(int i=0;i<8;i++){
    	    str.append(random.nextInt(10));
    	}
    	//将字符串转换为数字并输出
    	return str.toString();
    }
  

    public static void main(String[] args) {  
    	Map<String,Integer> map = new HashedMap();
    	Map<String,Integer> map2 = new HashedMap();
    	Map<String,Integer> map3 = new HashedMap();
        for (int i = 0; i < 8000; i++) {  
        	String str = generateNumber();
        	if(map.containsKey(str))
        		map.put(str, map.get(str)+1);
        	else
        		map.put(str, 1);
        	String str2 = generateNumber2();
        	if(map2.containsKey(str2))
        		map2.put(str2, map2.get(str2)+1);
        	else
        		map2.put(str2, 1);
        	String str3 = getStr();
        	if(map3.containsKey(str3))
        		map3.put(str3, map3.get(str3)+1);
        	else
        		map3.put(str3, 1);
        }  
        
        List<Integer> list = new ArrayList();
        for(Entry<String, Integer> entry:map.entrySet()) {
        	if(entry.getValue()>1)
        		list.add(entry.getValue());
        }
        
        List<Integer> list2 = new ArrayList();
        for(Entry<String, Integer> entry:map2.entrySet()) {
        	if(entry.getValue()>1)
        		list2.add(entry.getValue());
        }
        
        List<Integer> list3 = new ArrayList();
        for(Entry<String, Integer> entry:map3.entrySet()) {
        	if(entry.getValue()>1) {
        		list3.add(entry.getValue());
        		System.out.println(entry.getKey());
        	}
        }

        System.out.println("size:"+map.size()+"   "+list);
        System.out.println("size2:"+map2.size()+"   "+list2);
        System.out.println("size3:"+map3.size()+"   "+list3);
    }  

}  
