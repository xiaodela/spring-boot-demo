package test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @date 2018/4/18 19:58
 */
public class HttpUrlTest {

    public static String url = "http://127.0.0.1:8080/es/add";
    public static String url_1 = "http://127.0.0.1:8080/es/query";
    public static String url_2 = "http://127.0.0.1:8080/es/delete";


    public static void main(String[] args) {

        test_1();
    }

    public static void test_1() {

        Map<String, String> paraMap = new ConcurrentHashMap<String, String>();
        paraMap.put("test", "test");

        String result = doPost(url_1, paraMap, "utf-8");

        System.out.println(result);


    }

    public void test_2() {
    }

    public void test_3() {
    }

    public void test_4() {
    }

    public void test_5() {
    }

    public void test_6() {
    }

    public void test_7() {
    }

    public void test_8() {
    }

    public void test_9() {
    }

    public void test_10() {
    }

    public void test_11() {
    }

    public void test_12() {
    }

    public static String doPost(String url, Map<String, String> map, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


}
