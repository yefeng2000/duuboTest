package com.example.demo.job.quartz;

import com.example.demo.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * quartz Job定义方式一： 这种定义方式有一个缺陷，就是无法传参
 * @author wj
 */
@Component
public class QuartzJob {

    public void sayHello(){
        System.out.println("QuartzJob -------"+(DateUtils.forDatetime(new Date()))+"-------定义方式一");
    }
}
