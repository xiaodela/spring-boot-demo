package com.l9e.transaction.service.impl;

import com.alibaba.fastjson.JSON;
import com.l9e.transaction.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 二进制操作
 *
 * @author meizs
 * @create 2018-04-13 16:30
 **/
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplateNew;

    @Override
    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public <T> boolean setList(String key, List<T> list) {
        String value = JSON.toJSONString(list);
        return set(key, value);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            List<T> list = JSON.parseArray(json, clz);
            return list;
        }
        return null;
    }

    @Override
    public long lpush(final String key, Object obj) {
        final String value = JSON.toJSONString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public long rpush(final String key, Object obj) {
        final String value = JSON.toJSONString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res = connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }

    //**********************************************************************************************************//
    @Override
    public boolean setNew(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplateNew.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean setNew(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplateNew.opsForValue();
            operations.set(key, value);
            redisTemplateNew.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean exists(final String key) {
        return redisTemplateNew.hasKey(key);
    }

    @Override
    public void deleteNew(final String key) {
        if (exists(key)) {
            redisTemplateNew.delete(key);
        }
    }

    @Override
    public Object getNew(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplateNew.opsForValue();
        result = operations.get(key);
        return result;
    }

    @Override
    public void hmSetNew(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplateNew.opsForHash();
        hash.put(key, hashKey, value);
    }

    @Override
    public Object hmGetNew(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplateNew.opsForHash();
        return hash.get(key, hashKey);
    }

    @Override
    public void lPushNew(String k, Object v) {
        ListOperations<String, Object> list = redisTemplateNew.opsForList();
        list.leftPush(k, v);
    }

    @Override
    public List<Object> lRangeNew(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplateNew.opsForList();
        return list.range(k, l, l1);
    }

    @Override
    public void addNew(String key, Object value) {
        SetOperations<String, Object> set = redisTemplateNew.opsForSet();
        set.add(key, value);
    }

    @Override
    public Set<Object> setMembersNew(String key) {
        SetOperations<String, Object> set = redisTemplateNew.opsForSet();
        return set.members(key);
    }

    @Override
    public void zAddNew(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplateNew.opsForZSet();
        zset.add(key, value, scoure);
    }

    @Override
    public Set<Object> rangeByScoreNew(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplateNew.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }


}
