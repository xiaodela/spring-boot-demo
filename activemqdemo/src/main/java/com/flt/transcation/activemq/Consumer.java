package com.flt.transcation.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author meizs
 * @create 2018-04-27 16:52
 **/
@Service
public class Consumer {


    @JmsListener(destination = "mytest.queue")
    public void receiveQueue(String text) {
        System.out.println("Consumer收到的报文为:" + text);
    }


}
