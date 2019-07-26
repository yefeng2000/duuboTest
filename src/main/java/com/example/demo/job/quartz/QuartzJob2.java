package com.example.demo.job.quartz;
import com.example.demo.service.HelloService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * quartzJob定义方式二：和第1种方式相比，这种方式支持传参，任务启动时，executeInternal 方法将会被执行
 * @author wj
 */
public class QuartzJob2 extends QuartzJobBean {

    private HelloService helloService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        helloService.sayHello();
    }

    public HelloService getHelloService() {
        return helloService;
    }

    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }
}
