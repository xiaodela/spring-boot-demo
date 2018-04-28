package com.flt.transcation.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2018/4/27 20:36
 */
@Service
public class Consumer {

    @JmsListener(destination = "test.queue")
    public void receiveMsg(String text) {

        System.out.println("<<<<<<============ 收到消息： " + text);


    }
}