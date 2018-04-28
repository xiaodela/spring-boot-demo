package com.flt.bl.job;

import com.flt.bl.service.IMessageQueueService;
import com.flt.constant.MQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @date 2018/4/19 20:57
 */
@Component
@EnableScheduling
public class ScheduledTasks {
    private Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private IMessageQueueService messageQueueService;
    int cronCount = 0;


    @Scheduled(cron = "0 0/1 * * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
    public void testCron() {
        logger.info("===initialDelay: 第{}次执行方法", cronCount++);

        messageQueueService.send(MQConstant.HELLO_QUEUE_NAME, "测试发送消息");
        messageQueueService.send(MQConstant.HELLO_QUEUE_NAME, "测试延迟发送消息", 6000);
    }


}
