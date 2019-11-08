package com.example.demo.service.impl;

import java.util.Map;

import com.example.demo.service.DubboTestService;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.demo.util.RedisUtil;
@Service(version = "1.0.1")
public class DubboTestServiceImpl implements DubboTestService {

	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public String getStr(String test) {
		System.out.println("getStr method:"+test);
		return "dubbo 调用成功，返回数据："+test;
	}

	@Override
	public Map<Object, Object> getMap(String key) {
		System.out.println("getMap method:"+key+"  value:"+redisUtil.hmget(key));
		return redisUtil.hmget(key);
	}

}
