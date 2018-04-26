package com.l9e;

import com.l9e.transaction.config.ConfigPro;
import com.l9e.transaction.service.RedisService;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author meizs
 * @create 2018-04-13 16:23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisApplicationTests {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService service;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplateNew;

    @Autowired
    private ConfigPro config1;

    @Autowired
    private RestTemplate restTemplate;


    //@Test
    public void contextLoads() {

        //service.setNew("set001", "000111");
        //service.hmSetNew("hmap", "000", "111");
        // service.hmSetNew("hmap", "111", "111");
        // service.hmSetNew("hmap", "222", "111");

       /* service.zAddNew("zset001", "000", 1);
        service.zAddNew("zset001", "001", 2);*/


       /* SetOperations<String, Object> setOperations = redisTemplateNew.opsForSet();
        for (int i = 0; i < 5; i++) {

            setOperations.add("set2", "aa" + i);
        }*/


       /* Set<Object> aset = service.setMembersNew("set1");

        for (Object o : aset) {

            Account account = (Account) o;
            System.out.println(account);

        }
*/

       /* for (int i = 0; i < 5; i++) {
            service.rpush("r_1","ss"+i);
        }*/


        System.out.println(config1.getName());


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}