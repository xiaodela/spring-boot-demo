package com.flt.transcation.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsg(String destinationName, String message) {

        logger.info("============>>>>> 发送queue消息 " + message);

        Destination destination = new ActiveMQQueue(destinationName);

        jmsMessagingTemplate.convertAndSend(destination, message);

    }
}
