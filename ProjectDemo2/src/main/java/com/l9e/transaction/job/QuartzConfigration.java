package com.l9e.transaction.job;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author meizs
 * @create 2018-04-04 16:12
 **/
//@Configuration
public class QuartzConfigration {


    @Bean(name = "sendRefundJobDetail")
    public MethodInvokingJobDetailFactoryBean refundTicketJobDetail(RefundTicketJob job) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(job);
        jobDetail.setTargetMethod("sendRefund");
        return jobDetail;
    }

    @Bean(name = "changeTicketJobDetail")
    public MethodInvokingJobDetailFactoryBean changeTicketJobDetail(ChangeTicketJob job) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(job);
        jobDetail.setTargetMethod("changeTicket");
        return jobDetail;
    }

    @Bean(name = "changeTicketCronTriggerBean")
    public CronTriggerFactoryBean cronTriggerBean(JobDetail changeTicketJobDetail) {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(changeTicketJobDetail);
        tigger.setCronExpression("0/5 * * * * ? ");//每5秒执行一次
        return tigger;
    }

    @Bean(name = "sendRefundCronTriggerBean")
    public CronTriggerFactoryBean sendRefundCronTriggerBean(JobDetail sendRefundJobDetail) {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(sendRefundJobDetail);
        tigger.setCronExpression("0/5 * * * * ? ");//每5秒执行一次
        return tigger;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger sendRefundCronTriggerBean, Trigger changeTicketCronTriggerBean) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        bean.setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(sendRefundCronTriggerBean, changeTicketCronTriggerBean);
        return bean;
    }


}