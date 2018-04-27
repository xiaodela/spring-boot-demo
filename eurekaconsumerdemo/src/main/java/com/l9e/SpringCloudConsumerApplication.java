package com.l9e;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author meizs
 * @create 2018-04-24 17:39
 **/
@SpringBootApplication
//启用服务注册与发现
@EnableDiscoveryClient
//启用feign进行远程调用
@EnableFeignClients
public class SpringCloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerApplication.class, args);
    }
}