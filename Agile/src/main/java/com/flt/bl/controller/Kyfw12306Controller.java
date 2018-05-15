package com.flt.bl.controller;

import com.flt.bl.thread.KyfwBlThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author meizs
 * @create 2018-05-02 15:54
 **/
@RestController
@RequestMapping(value = "/kyfw")
public class Kyfw12306Controller {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ApplicationContext applicationContext;


    @RequestMapping(value = "/getOrder.do")
    public String queryOrderInfo() {

        KyfwBlThread instance = (KyfwBlThread) applicationContext.getBean("kyfwBlThread");
        Future<String> future = KyfwHodler.kyfwPool.submit(instance);
        String result = "";
        try {
            result = future.get();
        } catch (InterruptedException e) {
            log.info("InterruptedException", e);
        } catch (ExecutionException e) {
            log.info("ExecutionException", e);
        }

        return result;


    }

    static class KyfwHodler {
        private static ExecutorService kyfwPool = Executors.newFixedThreadPool(100);
    }


    public static void main(String[] args) {

    }





}
