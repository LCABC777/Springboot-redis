package com.lc.service;

import com.lc.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 一周有多少秒
     */
    private static final long WEEK_SECONDS = 7 * 24 * 60 * 60;


    /**
     * 将 key，value 存放到redis数据库中，默认设置过期时间为一周
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, JsonUtil.convertObj2String(value), WEEK_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public void set(String key, Object value, long expireTime) {
        redisTemplate.opsForValue().set(key, JsonUtil.convertObj2String(value), expireTime, TimeUnit.SECONDS);
    }

    /**
     * 存入多组key value
     * @param map
     */
    public void set(Map<String,String> map) {
      redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 判断 key 是否在 redis 数据库中
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取与 key 对应的对象
     * @param key
     * @param clazz 目标对象类型
     * @param <T>
     * @return
     */
  /*  public <T> T get(String key, Class<T> clazz) {
        String s = get(key);
        if (s == null) {
            return null;
        }
        return JsonUtil.convertString2Obj(s, clazz);
    }*/

    /**
     * 获取 key 对应的字符串
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除 key 对应的 value
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void setHash(String hashName,Map<String,String> map) {
       redisTemplate.opsForHash().putAll(hashName,map);
    }

    @Cacheable(cacheNames = "users",keyGenerator ="myKeyGenerator")
    public Map<Object,Object> getHash(String hashName){
        if (redisTemplate.hasKey(hashName)) {
            System.out.println(redisTemplate.opsForHash().entries(hashName));
            return redisTemplate.opsForHash().entries(hashName);
        }else {
            return null;
        }
    }
}