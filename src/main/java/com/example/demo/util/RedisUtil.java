package com.example.demo.util;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
/**
 * redicache 工具类
 * 
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtil {
@SuppressWarnings("rawtypes")
@Autowired
private RedisTemplate redisTemplate;
/**
 * 批量删除对应的value
 * 
 * @param keys
 */
public void remove(final String... keys) {
    for (String key : keys) {
    remove(key);
    }
}
/**
 * 批量删除key
 * 
 * @param pattern
 */
public void removePattern(final String pattern) {
    Set<Serializable> keys = redisTemplate.keys(pattern);
    if (keys.size() > 0)
    redisTemplate.delete(keys);
}
/**
 * 删除对应的value
 * 
 * @param key
 */
public void remove(final String key) {
    if (exists(key)) {
    redisTemplate.delete(key);
    }
}
/**
 * 判断缓存中是否有对应的value
 * 
 * @param key
 * @return
 */
public boolean exists(final String key) {
    return redisTemplate.hasKey(key);
}
/**
 * 读取缓存
 * 
 * @param key
 * @return
 */
public Object get(final String key) {
    Object result = null;
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
    result = operations.get(key);
    if(result==null){
        return null;
    }
    return result;
}
/**
 * 写入缓存
 * 
 * @param key
 * @param value
 * @return
 */
public boolean set(final String key, Object value) {
    boolean result = false;
    try {
    ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
    operations.set(key, value);
    result = true;
    } catch (Exception e) {
    e.printStackTrace();
    }
    return result;
}
/**
 * 写入缓存
 * 
 * @param key
 * @param value
 * @return
 */
public boolean set(final String key, Object value, Long expireTime) {
    boolean result = false;
    try {
    ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
    operations.set(key, value);
    redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    result = true;
    } catch (Exception e) {
    e.printStackTrace();
    }
    return result;
    }

    public  boolean hmset(String key, Map<Object, Object> value) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().putAll(key, value);
            result = true;
        } catch (Exception e) {
        e.printStackTrace();
        }
        return result;
    }
    
    public  Map<Object,Object> hmget(String key) {
        Map<Object,Object> result =null;
        try {
            result=  redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
        e.printStackTrace();
        }
        return result;
    }

    public boolean tryLock(String lockKey, String clientId, long seconds){
        Boolean res = redisTemplate.opsForValue().setIfAbsent(lockKey,clientId);
        redisTemplate.expire(lockKey,seconds,TimeUnit.SECONDS);
        return res;
    }

    public boolean releaseLock(String lockKey, String clientId){
        if(redisTemplate.hasKey(lockKey)) {
            redisTemplate.delete(lockKey);
        }
        return true;
    }
}
