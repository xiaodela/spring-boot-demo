package com.l9e.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author meizs
 * @create 2018-04-04 11:16
 **/
public class HttpClientUtil {

    public static String sendHttpPost(String url, String body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setEntity(new StringEntity(body));

        CloseableHttpResponse response = httpClient.execute(httpPost);
     //   System.out.println(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
      //  System.out.println(responseContent);

        response.close();
        httpClient.close();
        return responseContent;
    }

}
