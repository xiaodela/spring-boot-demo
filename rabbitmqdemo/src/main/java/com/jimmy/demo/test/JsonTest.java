package com.jimmy.demo.test;

import com.flt.bl.bean.DLXMessage;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 * @date 2018/4/19 21:52
 */
public class JsonTest {

    public static void main(String[] args) {
        String content = "{\"times\":60000,\"queueName\":\"kshop.repeat.trade.queue\",\"exchange\":\"KSHOP\",\"content\":\"测试延迟发送消息\"}";

        DLXMessage message = (DLXMessage) JSONObject.toBean(JSONObject.fromObject(content), DLXMessage.class);

        System.out.println(message.getQueueName());
    }
}
