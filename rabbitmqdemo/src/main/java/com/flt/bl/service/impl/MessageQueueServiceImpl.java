package com.flt.bl.service.impl;

import com.flt.bl.bean.DLXMessage;
import com.flt.bl.service.IMessageQueueService;
import com.flt.constant.MQConstant;
import com.rabbitmq.tools.json.JSONUtil;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.amqp.AmqpException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2018/4/19 20:12
 */
@Service("messageQueueService")
public class MessageQueueServiceImpl implements IMessageQueueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queueName, String msg) {
        System.out.println(queueName+msg);
        rabbitTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE, queueName, msg);
    }

    @Override
    public void send(String queueName, String msg, final long times) {
        DLXMessage dlxMessage = new DLXMessage(queueName, msg, times);
        MessagePostProcessor processor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(times + "");
                return message;
            }
        };
        dlxMessage.setExchange(MQConstant.DEFAULT_EXCHANGE);
        rabbitTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE, MQConstant.DEFAULT_DEAD_LETTER_QUEUE_NAME, JSONObject.fromObject(dlxMessage).toString(), processor);
    }


}
