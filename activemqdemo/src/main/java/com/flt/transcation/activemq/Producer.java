package com.flt.transcation.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * @author Administrator
 * @date 2018/4/27 20:36
 */
@Service
public class Producer {

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsg(String destinationName, String message) {
        System.out.println("============>>>>> 发送queue消息 " + message);
        Destination destination = new ActiveMQQueue(destinationName);
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
