package com.example.demo.job.scheduled;

import com.example.demo.util.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Scheduled方式定义定时任务
 * @author wj
 */
@Component
public class ScheduledJob {

    /**
     * @Scheduled 注解开启一个定时任务,所有时间的单位都是毫秒
     * @Scheduled 注解也支持 cron 表达式，使用 cron 表达式，可以非常丰富的描述定时任务的时间。cron 表达式格式如下:
     * [秒] [分] [小时] [日] [月] [周] [年]
     * eg:@Scheduled(cron ="0/5 * * * * *")  每隔5分钟执行一次
     * fixedRate 表示任务执行之间的时间间隔，具体是指两次任务的开始时间间隔，即第二次任务开始时，第一次任务可能还没结束
     */
    @Scheduled(fixedRate=500000)
    public void fixedRate(){
        System.out.println("fixedRate>>>"+DateUtils.forDatetime(new Date()));
    }

    /**
     * fixedDelay 表示任务执行之间的时间间隔，具体是指本次任务结束到下次任务开始之间的时间间隔
     */
    @Scheduled(fixedDelay=800000)
    public void fixedDelay(){
        System.out.println("fixedDelay>>>"+DateUtils.forDatetime(new Date()));
    }

    /**
     * initialDelay 表示首次任务启动的延迟时间
     */
    @Scheduled(initialDelay=800000,fixedDelay= 7000)
    public void initialDelay(){
        System.out.println( "initialDelay>>>" + DateUtils.forDatetime(new Date()));
    }
}
