package com.flt;

import com.flt.transcation.activemq.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author meizs
 * @create 2018-04-27 16:49
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJmsApplicationTests {

    @Autowired
    private Producer producer;

    @Test
    public void contextLoads() throws InterruptedException {

        producer.sendMessage("mytest.queue", "myname is chhliu!!!");

    }

}
