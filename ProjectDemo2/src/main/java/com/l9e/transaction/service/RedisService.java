package com.l9e.transaction.service;

import java.util.List;
import java.util.Set;

/**
 * @author meizs
 * @create 2018-04-13 16:23
 **/
public interface RedisService {

    public boolean set(String key, String value);

    public String get(String key);

    public boolean expire(String key, long expire);

    public <T> boolean setList(String key, List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    public long lpush(String key, Object obj);

    public long rpush(String key, Object obj);

    public String lpop(String key);

    //**********************************************************************************************************//
    boolean setNew(String key, Object value);

    boolean setNew(String key, Object value, Long expireTime);

    boolean exists(String key);

    void deleteNew(String key);

    Object getNew(String key);

    void hmSetNew(String key, Object hashKey, Object value);

    Object hmGetNew(String key, Object hashKey);

    void lPushNew(String k, Object v);

    List<Object> lRangeNew(String k, long l, long l1);

    void addNew(String key, Object value);

    Set<Object> setMembersNew(String key);

    void zAddNew(String key, Object value, double scoure);

    Set<Object> rangeByScoreNew(String key, double scoure, double scoure1);
}


