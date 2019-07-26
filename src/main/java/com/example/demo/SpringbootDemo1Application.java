package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@ComponentScan({"com.jamonapi","com.example.demo"})
// 启用定时任务
@EnableScheduling
public class SpringbootDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemo1Application.class, args);
	}
}
