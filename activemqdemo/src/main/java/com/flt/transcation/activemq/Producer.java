package com.flt.transcation.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * @author meizs
 * @create 2018-04-27 16:53
 **/
@Service
public class Producer {


    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(String destinationName, final String message) {
        System.out.println(message);
        Destination destination = new ActiveMQQueue(destinationName);
        jmsMessagingTemplate.convertAndSend(destination, message);
    }


}
