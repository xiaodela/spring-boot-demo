package com.l9e.transcation.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author meizs
 * @create 2018-04-24 17:40
 **/
@FeignClient(name = "HELLO-SERVICE")
public interface HelloRemote {

    //restful api 调用
    @RequestMapping(value = "/hello/{name}")
    public String hello(@PathVariable("name") String name);

    //传统api调用
    //@GetMapping(value = "/hello")
    //public String hello(@RequestParam(value = "name") String name);
}

