package com.flt.transcation.controller;

import com.flt.transcation.activemq.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2018/4/27 20:31
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);//不序列化

    @Autowired
    private Producer producer;

    @RequestMapping("/helloworld")
    public String helloworld() {


        for (int i = 0; i < 10; i++) {
            logger.info("****{}", i);
            producer.sendMsg("test.queue", "Queue Message " + i);
        }

        return "helloworld";
    }
}
