package com.flt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author Administrator
 * @date 2018/4/27 20:14
 */
@SpringBootApplication
public class SpringbootApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootApplication.class);
    }


}