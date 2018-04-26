import com.alibaba.fastjson.JSON;
import com.l9e.util.HttpClientUtil;
import com.l9e.util.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author meizs
 * @create 2018-04-03 14:53
 **/
public class UrlTest {

    static String url_1 = "http://127.0.0.1:18061/refund/refundTicket.do";
    static String url_2 = "http://127.0.0.1:18061/refund/queryTicket.do";
    static String url_3 = "http://127.0.0.1:18061/account/add.do";
    static String url_4 = "http://127.0.0.1:18061/account/getAccountById.do";
    static String url_5 = "http://127.0.0.1:18061/account/get/id/15";
    static String url_6 = "http://127.0.0.1:18061/account/redis/setget";
    static String url_7 = "http://127.0.0.1:18061/account/es/add.do";
    static String url_8 = "http://127.0.0.1:18061/account/es/query.do";
    static String url_10 = "http://127.0.0.1:8763/hello/meizs";


    public static void main(String[] args) {

        while (true) {
            StringBuilder post_param = new StringBuilder();
            String result = HttpUtil.sendByGet(url_10, "30000", "30000", "utf-8");
            System.out.println(result);
        }


    }

    public static void test_9() {
        StringBuilder post_param = new StringBuilder();
        String result = HttpUtil.sendByPost(url_8, post_param.toString(), "30000", "30000", "utf-8");
        System.out.println(result);
    }

    public static void url_6() {
        StringBuilder post_param = new StringBuilder();
        post_param.append("value=").append("***??//在");
        String result = HttpUtil.sendByPost(url_6, post_param.toString(), "30000", "30000", "utf-8");
        System.out.println(result);
    }

    public static void test_5() {
        StringBuilder post_param = new StringBuilder();
        String result = HttpUtil.sendByPost(url_5, post_param.toString(), "30000", "30000", "utf-8");
        System.out.println(result);
    }

    public static void test_4() {
        StringBuilder post_param = new StringBuilder();
        post_param.append("id=").append("15").append("&");
       /* post_param.append("account=").append("blue***").append("&");*///参数无法解析
        post_param.append("pwd=").append("blue***").append("&");
        post_param.append("createTime=").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
        String result = HttpUtil.sendByPost(url_4, post_param.toString(), "30000", "30000", "utf-8");
        System.out.println(result);
    }

    public static void test_3() {
        StringBuilder post_param = new StringBuilder();

        //post_param.append("id=").append("11").append("&");
        post_param.append("account=").append("blue***").append("&");
        post_param.append("pwd=").append("blue***").append("&");
        post_param.append("createTime=").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));

        String result = HttpUtil.sendByPost(url_3, post_param.toString(), "30000", "30000", "utf-8");
        System.out.println(result);
    }

    public static void test_2() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "000");
        map.put("message", "");
        map.put("status", true);

        String body = JSON.toJSONString(map);
        //System.out.println(body);
        try {
            String result = HttpClientUtil.sendHttpPost(url_2, body);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test_1() {
        String result = HttpUtil.sendByPost(url_1, "", "30000", "30000", "utf-8");
        System.out.println(result);
    }
}
