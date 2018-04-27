package com.flt.bl.processor;

import com.flt.bl.bean.DLXMessage;
import com.flt.bl.service.IMessageQueueService;
import com.flt.constant.MQConstant;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @date 2018/4/19 20:24
 */
@Component
@RabbitListener(queues = MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME)
public class TradeProcessor {

    @Autowired
    private IMessageQueueService messageQueueService;

    @RabbitHandler
    public void process(String content) {
        System.out.println(content);
        try {
            DLXMessage message = (DLXMessage) JSONObject.toBean(JSONObject.fromObject(content), DLXMessage.class);
            System.out.println(message.getContent());
             messageQueueService.send(message.getQueueName(), message.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
