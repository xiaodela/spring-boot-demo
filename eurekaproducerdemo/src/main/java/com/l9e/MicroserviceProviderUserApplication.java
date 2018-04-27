package com.l9e;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meizs
 * @create 2018-04-24 15:24
 **/
@SpringBootApplication
@EnableEurekaClient
@RestController
public class MicroserviceProviderUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceProviderUserApplication.class, args);
    }

    @RequestMapping(value = "/hello/{name}")
    public String hello(@PathVariable String name) {
        System.out.println(name + " welcome . My is microservice provider user");
        return name + " welcome . My is microservice provider user";
    }
}
