package com.l9e;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author meizs 启动应用程序
 */


@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) /*,HibernateJpaAutoConfiguration.class*/
//@EnableTransactionManagement
//hibernate
//@EnableJpaRepositories(basePackages = "com.l9e.transaction.dao")
//@EntityScan(basePackages = "com.l9e.transaction.bean")
//mybatis
//@MapperScan(basePackages = "com.l9e.transaction.xml")
public class AppMain {

    public static void main(String[] args) {

        SpringApplication.run(AppMain.class, args);

    }

}
