package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.jamonapi","com.example.demo"})
public class SpringbootDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemo1Application.class, args);
	}
}
