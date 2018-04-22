package com.example.demo.service;

import java.util.Map;

public interface DubboTestService {

	String getStr(String test);
	
	Map<Object,Object> getMap(String key);
}
