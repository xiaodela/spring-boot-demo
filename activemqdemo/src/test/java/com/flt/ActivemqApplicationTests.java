package com.flt;

import com.flt.transcation.activemq.Producer;
import com.flt.transcation.activemq.Publisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @date 2018/4/27 20:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivemqApplicationTests {
    @Resource
    private Producer producer;
    @Resource
    private Publisher publisher;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 10; i++) {
            producer.sendMsg("test.queue", "Queue Message " + i);
        }
    }

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            publisher.publish("test.topic", "Topic Message " + i);
        }
    }
}
