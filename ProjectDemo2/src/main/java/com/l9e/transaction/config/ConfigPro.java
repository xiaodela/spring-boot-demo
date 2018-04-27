package com.l9e.transaction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author meizs
 * @create 2018-04-16 17:10
 **/
@Component
@PropertySource("classpath:config1.properties")//可以指定配置文件
@ConfigurationProperties(prefix = "spring.c1")
public class ConfigPro {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}