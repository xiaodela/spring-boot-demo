package com.flt.bl.processor;

import com.flt.constant.MQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @date 2018/4/19 20:16
 */
@Component
@RabbitListener(queues = MQConstant.HELLO_QUEUE_NAME)
public class HelloProcessor {

    @RabbitHandler
    public void process(String content) {
        System.out.println("接受消息:" + content);
    }
}