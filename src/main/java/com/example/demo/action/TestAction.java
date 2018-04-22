package com.example.demo.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.util.RedisUtil;

@RestController
public class TestAction {
	
	@Autowired
    private RedisUtil redisUtil;
	@Reference(version="1.0.1")
	
	@GetMapping("/test")
	public Map<Object,Object> test(){
		Map<Object,Object> map = new HashMap<>();
		map.put("hello", "world");
		map.put("你好", "地球");
		Object m = redisUtil.hmget("map");
		if(m== null)
			redisUtil.hmset("map", map);
		else {
			Map<String,Object> mp = (Map<String,Object>)m;
			if(mp.isEmpty())
				redisUtil.hmset("map", map);
			System.out.println(m);
			
		}
		return map;
	}

}
