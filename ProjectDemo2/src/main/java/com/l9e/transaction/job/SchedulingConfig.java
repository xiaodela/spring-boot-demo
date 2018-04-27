package com.l9e.transaction.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author meizs
 * @create 2018-04-19 15:47
 **/
@Configuration
@EnableScheduling
public class SchedulingConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    //@Scheduled(cron = "0/10 * * * * ?") // 每10s执行一次
    public void getToken() {
        logger.info("getToken定时任务启动");
    }

}