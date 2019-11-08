package com.example.demo.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.util.IDCardValidServiceTest;
import com.example.demo.util.RedisUtil;
@Api(tags="用户管理相关接口")
@RestController
public class TestAction {

	private final static Logger LOGGER = LoggerFactory.getLogger(TestAction.class);

	@Autowired
    private RedisUtil redisUtil;
	//@Reference(version="1.0.1")
	@Autowired
	private IDCardValidServiceTest idCardValidService;
	
	@GetMapping("/test")
	@ApiOperation("根据id更新用户的接口")
	public Map<Object,Object> test() throws Exception{
		Map<Object,Object> map = new HashMap<>(2);
		map.put("hello", "world");
		map.put("你好", "地球");
		Object m = redisUtil.hmget("map");
		if(m== null) {
			;//redisUtil.hmset("map", map);
		} else {
			Map<String,Object> mp = (Map<String,Object>)m;
			if(mp.isEmpty()) {
				;//redisUtil.hmset("map", map);
			}
			System.out.println(m);
			
		}
		return map;
	}

	@GetMapping("/hello/{name}")
	@ApiOperation("根据id查询用户的接口")
	@ApiImplicitParam(name="name",value="用户name",defaultValue="99",required=true)
	public String hello(@PathVariable("name") String name,/*@ApiParam(name = "say",value = "内容",required = false)*/ String say){
		User user = new User();
		user.setCreateTime(new Date());
		user.setLoginName("test");
		user.setNickName("风雨兼程");
		user.setLoginPwd("8888888888");
		user.setType("1");
		LOGGER.info("请求参数信息：{}",JSON.toJSONStringWithDateFormat(user,"yyyy-MM-dd HH:mm:ss",SerializerFeature.WriteDateUseDateFormat));

		return "用户："+name+" =="+say;
	}

}
