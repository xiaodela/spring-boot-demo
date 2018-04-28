package com.flt.bl.service;

/**
 * @author Administrator
 * @date 2018/4/19 20:12
 */
public interface IMessageQueueService {

    /**
     * 发送消息到队列
     *
     * @param queue   队列名称
     * @param message 消息内容
     */
    public void send(String queueName, String message);

    /**
     * 延迟发送消息到队列
     *
     * @param queue   队列名称
     * @param message 消息内容
     * @param times   延迟时间 单位毫秒
     */
    public void send(String queueName, String message, final long times);

}