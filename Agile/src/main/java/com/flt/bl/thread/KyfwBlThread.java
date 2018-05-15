package com.flt.bl.thread;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author meizs
 * @create 2018-05-02 16:16
 **/
@Component("kyfwBlThread")
@Scope
public class KyfwBlThread implements Callable<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String call() throws Exception {
        HashMap<String, Object> mapJson = new HashMap<>();
        mapJson.put("orderId", UUID.randomUUID().toString());
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id", "");
        map1.put("name", "");
        map1.put("type", "");
        list.add(map1);
        mapJson.put("tickets", list);
        logger.info(JSON.toJSONString(mapJson));
        return JSON.toJSONString(mapJson);
    }


    public static void main(String[] args) {


    }


}
