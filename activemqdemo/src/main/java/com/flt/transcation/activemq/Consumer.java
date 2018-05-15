package com.flt.transcation.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2018/4/27 20:36
 */
@Service
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @JmsListener(destination = "test.queue")
    public void receiveMsg(String text) {

        logger.info("<<<<<<============ 收到消息： " + text);


    }
}