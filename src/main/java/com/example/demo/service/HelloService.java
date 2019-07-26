package com.example.demo.service;

import com.example.demo.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloService {

    public void sayHello(){
        System.out.println("QuartzJob ======"+(DateUtils.forDatetime(new Date()))+"=====定义方式二");
    }
}
