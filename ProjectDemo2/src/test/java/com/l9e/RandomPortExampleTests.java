package com.l9e;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * @author meizs
 * @create 2018-04-13 14:47
 **/
/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/
public class RandomPortExampleTests {

    @Autowired
    private TestRestTemplate restTemplate;

   // @Test
    public void exampleTest() {
        String body = this.restTemplate.getForObject("/refund/refundTicket.do", String.class);
        System.out.println(body);
      //  assertThat(body).isEqualTo("Hello World");
    }

}
