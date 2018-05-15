package com.flt;

import com.flt.common.httpclient.KyfwClient;
import com.flt.common.httpclient.KyfwClientBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author meizs
 * @create 2018-05-15 14:21
 **/
public class UrlTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    String url = "http://127.0.0.1:8080/helloworld";

                    KyfwClient client = KyfwClientBuilder.create().build();

                    String result = client.post(url, null);

                    System.out.println(result);

                }
            });
        }

    }

}
