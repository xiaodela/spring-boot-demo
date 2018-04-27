package com.l9e.transcation.controller;

import com.l9e.transcation.service.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meizs
 * @create 2018-04-24 17:41
 **/
@RestController
public class ConsumerController {

    @Autowired
    HelloRemote helloRemote;

    @RequestMapping(value = "/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return helloRemote.hello(name);
    }
}