package com.example.demo;

/**
 * @ClassName:  RunTest
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 寻找手艺人
 * @email:  maker2win@163.com
 * @date:   2019年5月17日 上午9:13:45
 *
 * @Copyright: 2019 www.maker-win.net Inc. All rights reserved.
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RunTest implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunTest.class);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("执行多线程测试");
        String url="http://localhost:8088/hello/test";
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for(int i=0; i<10; i++){
            String userId = "userId" + i;
            HttpEntity request = buildRequest(userId);
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    System.out.println("Thread:"+Thread.currentThread().getName()+", time:"+formatDate(new Date()));
                    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                    System.out.println("Thread:"+Thread.currentThread().getName()+", time:"+formatDate(new Date()) + "," + response.getBody());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.countDown();
        System.out.println("============主线程 Run Over !!!!!=========");
    }

    private HttpEntity buildRequest(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "yourToken");
        Map<String, Object> body = new HashMap<>();
        body.put("userId", userId);
        return new HttpEntity<>(body, headers);
    }

    private String formatDate(Date date){
        return dateFormat.format(date);
    }

}
