package com.flt.commom.httpclient;

import com.sun.deploy.net.URLEncoder;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * @author meizs
 * @create 2018-05-02 17:12
 **/
public class KyfwClient {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private CookieStore cookieStore;
    private CloseableHttpClient client;
    private RequestConfig requestConfig;


    //无代理，无cookie
    public KyfwClient() {
        requestConfig = this.getRequestConfig(getRequestConfigBuilder());
        client = this.getHttpClient(getHttpClientBuilder());
    }

    //cookie客户端
    public KyfwClient(List<Map<String, String>> cookieMapList) {
        cookieStore = this.getCookieStore(this.getBasicClientCookieList(cookieMapList));
        requestConfig = this.getRequestConfig(getRequestConfigBuilder());
        client = this.getHttpClient(getCookieHttpClientBuilder());
    }

    //代理客户端
    public KyfwClient(Map<String, Object> proxyMap) {
        requestConfig = this.getRequestConfig(getRequestConfigBuilder());
        client = this.getHttpClient(this.getProxyHttpClientBuilder(proxyMap));
    }

    //代理，cookie客户端
    public KyfwClient(List<Map<String, String>> cookieMapList, Map<String, Object> proxyMap) {
        cookieStore = this.getCookieStore(this.getBasicClientCookieList(cookieMapList));
        requestConfig = this.getRequestConfig(getRequestConfigBuilder());
        client = this.getHttpClient(this.getCookieProxyHttpClientBuilder(proxyMap));
    }


    public static void main(String[] args) {
        //https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2018-05-22&leftTicketDTO.from_station=XFB&leftTicketDTO.to_station=DFB&purpose_codes=ADULT
        //https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&0.3360564314840555 HTTP/1.1
        //g_strUpMaskAddr = "http://219.238.151.222:18010/partner/sendCode.do"
        //g_strDownMaskAddr = "http://219.238.151.222:18010/partner/requestResult.do"
        //g_strfeedBackAddr = "http://219.238.151.222:18010/partner/feedBack.do"

        test5();

    }

    private static void test8() {
        Map<String, Object> proxyMap = new LinkedHashMap<>();
        proxyMap.put("userName", "hcpkuyou19e");
        proxyMap.put("passWord", "hcpkuyou19e");
        proxyMap.put("ip", "127.0.0.1");
        proxyMap.put("port", 8888);


        String url = "http://219.238.151.222:18010/partner/feedBack.do";
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put("partner_id", "19e");
        paramMap.put("pic_id", "PIC1805041846355899026");
        paramMap.put("result_status", "1");

        KyfwClient kyfwClient = new KyfwClient(proxyMap);

        String result = kyfwClient.get(url, kyfwClient.getUrlParams(paramMap));
        System.out.println(result);
    }

    private static void test7() {
        Map<String, Object> proxyMap = new LinkedHashMap<>();
        proxyMap.put("userName", "hcpkuyou19e");
        proxyMap.put("passWord", "hcpkuyou19e");
        proxyMap.put("ip", "127.0.0.1");
        proxyMap.put("port", 8888);


        String url = "http://219.238.151.222:18010/partner/requestResult.do";
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put("partner_id", "19e");
        paramMap.put("pic_id", "PIC1805041846355899026");


        KyfwClient kyfwClient = new KyfwClient(proxyMap);

        String result = kyfwClient.get(url, kyfwClient.getUrlParams(paramMap));
        System.out.println(result);
    }

    private static void test6() {
        Map<String, Object> proxyMap = new LinkedHashMap<>();
        proxyMap.put("userName", "hcpkuyou19e");
        proxyMap.put("passWord", "hcpkuyou19e");
        proxyMap.put("ip", "127.0.0.1");
        proxyMap.put("port", 8888);


        String url = "http://219.238.151.222:18010/partner/sendCode.do";
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put("order_id", "kyfw" + Math.random());
        paramMap.put("span_time", "60");
        paramMap.put("partner_id", "19e");

        KyfwClient kyfwClient = new KyfwClient(proxyMap);

        //  System.out.println(kyfwClient.get("https://kyfw.12306.cn/otn/login/init", ""));
        //  System.out.println(kyfwClient.get(url, ""));

        byte[] fileByte = kyfwClient.getFileByte();
        if (fileByte.length > 10000) {
            String result = kyfwClient.post_uploadImage(url, paramMap, fileByte);
            System.out.println(result);
        }
    }

    private static void test5() {
        Map<String, String> BIGipServerotn = new LinkedHashMap<String, String>();
        BIGipServerotn.put("name", "BIGipServerotn");
        BIGipServerotn.put("value", "66060810.38945.0000");
        BIGipServerotn.put("domain", "kyfw.12306.cn");
        BIGipServerotn.put("path", "/");


        Map<String, String> BIGipServerpool_passport = new LinkedHashMap<String, String>();
        BIGipServerpool_passport.put("name", "BIGipServerpool_passport");
        BIGipServerpool_passport.put("value", "250413578.50215.0000");
        BIGipServerpool_passport.put("domain", "kyfw.12306.cn");
        BIGipServerpool_passport.put("path", "/");

        Map<String, String> JSESSIONID = new LinkedHashMap<String, String>();
        JSESSIONID.put("name", "JSESSIONID");
        JSESSIONID.put("value", "5A2A2054F2BB2D8BF5E6C52A8C6FDF92");
        JSESSIONID.put("domain", "kyfw.12306.cn");
        JSESSIONID.put("path", "/otn");

        Map<String, String> RAIL_DEVICEID = new LinkedHashMap<String, String>();
        RAIL_DEVICEID.put("name", "RAIL_DEVICEID");
        RAIL_DEVICEID.put("value", "mAZyKnwdBAor-PjZuBIWaFBlte2DqNNAHYbtKljgLBZOr1rOxyBMh_AjImS2rUFYg0kxJ8wk6yl3Xu85925zJ7NRR1hyX0xv-sdKWo_c-PSQsHQ4ZNZsP1gMVwah37nzA8kcUJYfKgx9Jyx9qq-c-KKC1v_Y3UQ0");
        RAIL_DEVICEID.put("domain", "kyfw.12306.cn");
        RAIL_DEVICEID.put("path", "/");

        Map<String, String> RAIL_EXPIRATION = new LinkedHashMap<String, String>();
        RAIL_EXPIRATION.put("name", "RAIL_EXPIRATION");
        RAIL_EXPIRATION.put("value", "1525611099381");
        RAIL_EXPIRATION.put("domain", "kyfw.12306.cn");
        RAIL_EXPIRATION.put("path", "/");

        Map<String, String> route = new LinkedHashMap<String, String>();
        route.put("name", "route");
        route.put("value", "6f50b51faa11b987e576cdb301e545c4");
        route.put("domain", "kyfw.12306.cn");
        route.put("path", "/");

        Map<String, String> tk = new LinkedHashMap<String, String>();
        tk.put("name", "tk");
        tk.put("value", "QLh1HsjQ_cs2AR5WBlSKphXz82JCiTBsfX32kC-pjC0dq1210");
        tk.put("domain", "kyfw.12306.cn");
        tk.put("path", "/otn");


        List<Map<String, String>> listCookieMap = new ArrayList<Map<String, String>>();

        listCookieMap.add(BIGipServerotn);
        listCookieMap.add(BIGipServerpool_passport);
        listCookieMap.add(JSESSIONID);
        listCookieMap.add(RAIL_DEVICEID);
        listCookieMap.add(RAIL_EXPIRATION);
        listCookieMap.add(route);
        listCookieMap.add(tk);


        Map<String, Object> proxyMap = new LinkedHashMap<String, Object>();
        proxyMap.put("userName", "hcpkuyou19e");
        proxyMap.put("passWord", "hcpkuyou19e");
        proxyMap.put("ip", "127.0.0.1");
        proxyMap.put("port", 8888);

     /*   proxyMap.put("ip", "121.40.170.167");
        proxyMap.put("port", 808);*/

        KyfwClient kyfwClient = new KyfwClient(listCookieMap, proxyMap);

        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put("login_site", "E");
        paramMap.put("module", "login");
        paramMap.put("rand", "sjrand");
        StringBuilder paramStr = new StringBuilder();
        paramStr.append(kyfwClient.getUrlParams(paramMap)).append("&").append(Math.random());

        Map<String, String> headMap = new LinkedHashMap<>();
        headMap.put("Host", "kyfw.12306.cn");
        headMap.put("Connection", "keep-alive");
        headMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        headMap.put("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        headMap.put("Referer", "https://kyfw.12306.cn/otn/login/init");
        headMap.put("Accept-Encoding", "gzip, deflate, br");
        headMap.put("Accept-Language", "zh-CN,zh;q=0.9");


        String url = "https://kyfw.12306.cn/passport/captcha/captcha-image";

        for (int i = 0; i < 1; i++) {
            byte[] str = kyfwClient.get_downloadFile(url, paramStr.toString(), headMap);

            if (str.length < 10000) {
                System.out.println("byte is  zero!!!");
                continue;
            } else {
                System.out.println(str.length);
            }

            BufferedOutputStream bos = null;
            FileOutputStream fos = null;
            File file = null;

            File dir = new File("D:/image");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File("D:/image" + File.separator + Math.random() + ".jpeg");
            try {
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(str);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        CookieStore cookieStore = kyfwClient.getCookieStore();
        List<Cookie> listCookie = cookieStore.getCookies();
        StringBuilder bstr = new StringBuilder();
        int i = 0;
        for (Cookie ck : listCookie) {
            bstr.append(ck.getName()).append(",").append(ck.getValue()).append(",").append(ck.getDomain()).append(",").append(ck.getPath());
            if (i != listCookie.size() - 1) {
                bstr.append(";");
            }
            i++;
        }

        System.out.println(bstr);


    }

    private static void test4() {
        Map<String, String> BIGipServerotn = new LinkedHashMap<String, String>();
        BIGipServerotn.put("name", "BIGipServerotn");
        BIGipServerotn.put("value", "66060810.38945.0000");
        BIGipServerotn.put("domain", "kyfw.12306.cn");
        BIGipServerotn.put("path", "/");


        Map<String, String> BIGipServerpool_passport = new LinkedHashMap<String, String>();
        BIGipServerpool_passport.put("name", "BIGipServerpool_passport");
        BIGipServerpool_passport.put("value", "250413578.50215.0000");
        BIGipServerpool_passport.put("domain", "kyfw.12306.cn");
        BIGipServerpool_passport.put("path", "/");

        Map<String, String> JSESSIONID = new LinkedHashMap<String, String>();
        JSESSIONID.put("name", "JSESSIONID");
        JSESSIONID.put("value", "BE2689A38027681A55F144914DC2B9C7");
        JSESSIONID.put("domain", "kyfw.12306.cn");
        JSESSIONID.put("path", "/otn");

        Map<String, String> RAIL_DEVICEID = new LinkedHashMap<String, String>();
        RAIL_DEVICEID.put("name", "RAIL_DEVICEID");
        RAIL_DEVICEID.put("value", "mAZyKnwdBAor-PjZuBIWaFBlte2DqNNAHYbtKljgLBZOr1rOxyBMh_AjImS2rUFYg0kxJ8wk6yl3Xu85925zJ7NRR1hyX0xv-sdKWo_c-PSQsHQ4ZNZsP1gMVwah37nzA8kcUJYfKgx9Jyx9qq-c-KKC1v_Y3UQ0");
        RAIL_DEVICEID.put("domain", "kyfw.12306.cn");
        RAIL_DEVICEID.put("path", "/");

        Map<String, String> RAIL_EXPIRATION = new LinkedHashMap<String, String>();
        RAIL_EXPIRATION.put("name", "RAIL_EXPIRATION");
        RAIL_EXPIRATION.put("value", "1525611099381");
        RAIL_EXPIRATION.put("domain", "kyfw.12306.cn");
        RAIL_EXPIRATION.put("path", "/");

        Map<String, String> route = new LinkedHashMap<String, String>();
        route.put("name", "route");
        route.put("value", "6f50b51faa11b987e576cdb301e545c4");
        route.put("domain", "kyfw.12306.cn");
        route.put("path", "/");

        Map<String, String> tk = new LinkedHashMap<String, String>();
        tk.put("name", "tk");
        tk.put("value", "BzVoS2UBzukaUg2WiXdoGhVPMACo3BHA1Lklrx35w4Y641210");
        tk.put("domain", "kyfw.12306.cn");
        tk.put("path", "/otn");


        List<Map<String, String>> listCookieMap = new ArrayList<Map<String, String>>();

        listCookieMap.add(BIGipServerotn);
        listCookieMap.add(BIGipServerpool_passport);
        listCookieMap.add(JSESSIONID);
        listCookieMap.add(RAIL_DEVICEID);
        listCookieMap.add(RAIL_EXPIRATION);
        listCookieMap.add(route);
        listCookieMap.add(tk);


        Map<String, Object> proxyMap = new LinkedHashMap<String, Object>();
        proxyMap.put("userName", "hcpkuyou19e");
        proxyMap.put("passWord", "hcpkuyou19e");
        proxyMap.put("ip", "127.0.0.1");
        proxyMap.put("port", 8888);

     /*   proxyMap.put("ip", "121.40.170.167");
        proxyMap.put("port", 808);*/

        KyfwClient kyfwClient = new KyfwClient(listCookieMap, proxyMap);

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("_json_att", "");
        String url = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrderNoComplete";


        Map<String, String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        // headerMap.put("Cache-Control", "no-cache");
        headerMap.put("Accept", "*/*");
        headerMap.put("Origin", "https://kyfw.12306.cn");
        headerMap.put("X-Requested-With", "XMLHttpRequest");
        //headerMap.put("If-Modified-Since", "0");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/leftTicket/init");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");


        for (int i = 0; i < 5; i++) {
            //  Map<String, String> result = kyfwClient.post_resultMap(url, params, headerMap);
            String result = kyfwClient.post(url, params, headerMap);
            System.out.println(result);
        }
    }

    private static void test3() {
        Map<String, String> BIGipServerotn = new LinkedHashMap<String, String>();
        BIGipServerotn.put("name", "BIGipServerotn");
        BIGipServerotn.put("value", "66060810.38945.0000");
        BIGipServerotn.put("domain", "kyfw.12306.cn");
        BIGipServerotn.put("path", "/");


        Map<String, String> BIGipServerpool_passport = new LinkedHashMap<String, String>();
        BIGipServerpool_passport.put("name", "BIGipServerpool_passport");
        BIGipServerpool_passport.put("value", "250413578.50215.0000");
        BIGipServerpool_passport.put("domain", "kyfw.12306.cn");
        BIGipServerpool_passport.put("path", "/");

        Map<String, String> JSESSIONID = new LinkedHashMap<String, String>();
        JSESSIONID.put("name", "JSESSIONID");
        JSESSIONID.put("value", "5A2A2054F2BB2D8BF5E6C52A8C6FDF92");
        JSESSIONID.put("domain", "kyfw.12306.cn");
        JSESSIONID.put("path", "/otn");

        Map<String, String> RAIL_DEVICEID = new LinkedHashMap<String, String>();
        RAIL_DEVICEID.put("name", "RAIL_DEVICEID");
        RAIL_DEVICEID.put("value", "mAZyKnwdBAor-PjZuBIWaFBlte2DqNNAHYbtKljgLBZOr1rOxyBMh_AjImS2rUFYg0kxJ8wk6yl3Xu85925zJ7NRR1hyX0xv-sdKWo_c-PSQsHQ4ZNZsP1gMVwah37nzA8kcUJYfKgx9Jyx9qq-c-KKC1v_Y3UQ0");
        RAIL_DEVICEID.put("domain", "kyfw.12306.cn");
        RAIL_DEVICEID.put("path", "/");

        Map<String, String> RAIL_EXPIRATION = new LinkedHashMap<String, String>();
        RAIL_EXPIRATION.put("name", "RAIL_EXPIRATION");
        RAIL_EXPIRATION.put("value", "1525611099381");
        RAIL_EXPIRATION.put("domain", "kyfw.12306.cn");
        RAIL_EXPIRATION.put("path", "/");

        Map<String, String> route = new LinkedHashMap<String, String>();
        route.put("name", "route");
        route.put("value", "6f50b51faa11b987e576cdb301e545c4");
        route.put("domain", "kyfw.12306.cn");
        route.put("path", "/");

        Map<String, String> tk = new LinkedHashMap<String, String>();
        tk.put("name", "tk");
        tk.put("value", "QLh1HsjQ_cs2AR5WBlSKphXz82JCiTBsfX32kC-pjC0dq1210");
        tk.put("domain", "kyfw.12306.cn");
        tk.put("path", "/otn");


        List<Map<String, String>> listCookieMap = new ArrayList<Map<String, String>>();

        listCookieMap.add(BIGipServerotn);
        listCookieMap.add(BIGipServerpool_passport);
        listCookieMap.add(JSESSIONID);
        listCookieMap.add(RAIL_DEVICEID);
        listCookieMap.add(RAIL_EXPIRATION);
        listCookieMap.add(route);
        listCookieMap.add(tk);


        Map<String, Object> proxyMap = new LinkedHashMap<String, Object>();
        proxyMap.put("userName", "hcpkuyou19e");
        proxyMap.put("passWord", "hcpkuyou19e");
        proxyMap.put("ip", "127.0.0.1");
        proxyMap.put("port", 8888);

     /*   proxyMap.put("ip", "121.40.170.167");
        proxyMap.put("port", 808);*/

        KyfwClient kyfwClient = new KyfwClient(listCookieMap, proxyMap);

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("leftTicketDTO.train_date", "2018-05-22");
        params.put("leftTicketDTO.from_station", "XFB");
        params.put("leftTicketDTO.to_station", "DFB");
        params.put("purpose_codes", "ADULT");
        String url = "https://kyfw.12306.cn/otn/leftTicket/query";
        String urlParams = kyfwClient.getUrlParams(params);
        System.out.println(urlParams);


        Map<String, String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cache-Control", "no-cache");
        headerMap.put("Accept", "*/*");
        headerMap.put("X-Requested-With", "XMLHttpRequest");
        headerMap.put("If-Modified-Since", "0");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/leftTicket/init");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");


        for (int i = 0; i < 5; i++) {
            String result = kyfwClient.get(url, urlParams, headerMap);
            System.out.println(result);
        }
    }

    private static void test2() {
        Map<String, String> BIGipServerotn = new LinkedHashMap<String, String>();
        BIGipServerotn.put("name", "BIGipServerotn");
        BIGipServerotn.put("value", "66060810.38945.0000");
        BIGipServerotn.put("domain", "kyfw.12306.cn");
        BIGipServerotn.put("path", "/");


        Map<String, String> BIGipServerpool_passport = new LinkedHashMap<String, String>();
        BIGipServerpool_passport.put("name", "BIGipServerpool_passport");
        BIGipServerpool_passport.put("value", "250413578.50215.0000");
        BIGipServerpool_passport.put("domain", "kyfw.12306.cn");
        BIGipServerpool_passport.put("path", "/");

        Map<String, String> JSESSIONID = new LinkedHashMap<String, String>();
        JSESSIONID.put("name", "JSESSIONID");
        JSESSIONID.put("value", "C76C248AD3727D1C2C83495AAF0CB526");
        JSESSIONID.put("domain", "kyfw.12306.cn");
        JSESSIONID.put("path", "/");

        Map<String, String> RAIL_DEVICEID = new LinkedHashMap<String, String>();
        RAIL_DEVICEID.put("name", "RAIL_DEVICEID");
        RAIL_DEVICEID.put("value", "mAZyKnwdBAor-PjZuBIWaFBlte2DqNNAHYbtKljgLBZOr1rOxyBMh_AjImS2rUFYg0kxJ8wk6yl3Xu85925zJ7NRR1hyX0xv-sdKWo_c-PSQsHQ4ZNZsP1gMVwah37nzA8kcUJYfKgx9Jyx9qq-c-KKC1v_Y3UQ0");
        RAIL_DEVICEID.put("domain", "12306.cn");
        RAIL_DEVICEID.put("path", "/");

        Map<String, String> RAIL_EXPIRATION = new LinkedHashMap<String, String>();
        RAIL_EXPIRATION.put("name", "RAIL_EXPIRATION");
        RAIL_EXPIRATION.put("value", "1525611099381");
        RAIL_EXPIRATION.put("domain", "12306.cn");
        RAIL_EXPIRATION.put("path", "/");

        Map<String, String> route = new LinkedHashMap<String, String>();
        route.put("name", "route");
        route.put("value", "6f50b51faa11b987e576cdb301e545c4");
        route.put("domain", "kyfw.12306.cn");
        route.put("path", "/");


        List<Map<String, String>> listCookieMap = new ArrayList<Map<String, String>>();

        listCookieMap.add(BIGipServerotn);
        listCookieMap.add(BIGipServerpool_passport);
        listCookieMap.add(JSESSIONID);
        listCookieMap.add(RAIL_DEVICEID);
        listCookieMap.add(RAIL_EXPIRATION);
        listCookieMap.add(route);


        KyfwClient kyfwClient = new KyfwClient(listCookieMap);

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("leftTicketDTO.train_date", "2018-05-22");
        params.put("leftTicketDTO.from_station", "XFB");
        params.put("leftTicketDTO.to_station", "DFB");
        params.put("purpose_codes", "ADULT");
        String url = "https://kyfw.12306.cn/otn/leftTicket/query";
        String urlParams = kyfwClient.getUrlParams(params);
        System.out.println(urlParams);


        for (int i = 0; i < 5; i++) {
            String result = kyfwClient.get(url, urlParams);
            System.out.println(result);
        }
    }

    private static void test1() {
        KyfwClient kyfwClient = new KyfwClient();
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("leftTicketDTO.train_date", "2018-05-22");
        params.put("leftTicketDTO.from_station", "XFB");
        params.put("leftTicketDTO.to_station", "DFB");
        params.put("purpose_codes", "ADULT");
        String url = "https://kyfw.12306.cn/otn/leftTicket/query";
        String urlParams = kyfwClient.getUrlParams(params);
        System.out.println(urlParams);

        for (int i = 0; i < 5; i++) {
            String result = kyfwClient.get(url, urlParams);
            System.out.println(result);
        }
    }

    public byte[] getFileByte() {

        Map<String, String> BIGipServerotn = new LinkedHashMap<String, String>();
        BIGipServerotn.put("name", "BIGipServerotn");
        BIGipServerotn.put("value", "66060810.38945.0000");
        BIGipServerotn.put("domain", "kyfw.12306.cn");
        BIGipServerotn.put("path", "/");


        Map<String, String> BIGipServerpool_passport = new LinkedHashMap<String, String>();
        BIGipServerpool_passport.put("name", "BIGipServerpool_passport");
        BIGipServerpool_passport.put("value", "250413578.50215.0000");
        BIGipServerpool_passport.put("domain", "kyfw.12306.cn");
        BIGipServerpool_passport.put("path", "/");

        Map<String, String> JSESSIONID = new LinkedHashMap<String, String>();
        JSESSIONID.put("name", "JSESSIONID");
        JSESSIONID.put("value", "5A2A2054F2BB2D8BF5E6C52A8C6FDF92");
        JSESSIONID.put("domain", "kyfw.12306.cn");
        JSESSIONID.put("path", "/otn");

        Map<String, String> RAIL_DEVICEID = new LinkedHashMap<String, String>();
        RAIL_DEVICEID.put("name", "RAIL_DEVICEID");
        RAIL_DEVICEID.put("value", "mAZyKnwdBAor-PjZuBIWaFBlte2DqNNAHYbtKljgLBZOr1rOxyBMh_AjImS2rUFYg0kxJ8wk6yl3Xu85925zJ7NRR1hyX0xv-sdKWo_c-PSQsHQ4ZNZsP1gMVwah37nzA8kcUJYfKgx9Jyx9qq-c-KKC1v_Y3UQ0");
        RAIL_DEVICEID.put("domain", "kyfw.12306.cn");
        RAIL_DEVICEID.put("path", "/");

        Map<String, String> RAIL_EXPIRATION = new LinkedHashMap<String, String>();
        RAIL_EXPIRATION.put("name", "RAIL_EXPIRATION");
        RAIL_EXPIRATION.put("value", "1525611099381");
        RAIL_EXPIRATION.put("domain", "kyfw.12306.cn");
        RAIL_EXPIRATION.put("path", "/");

        Map<String, String> route = new LinkedHashMap<String, String>();
        route.put("name", "route");
        route.put("value", "6f50b51faa11b987e576cdb301e545c4");
        route.put("domain", "kyfw.12306.cn");
        route.put("path", "/");

        Map<String, String> tk = new LinkedHashMap<String, String>();
        tk.put("name", "tk");
        tk.put("value", "QLh1HsjQ_cs2AR5WBlSKphXz82JCiTBsfX32kC-pjC0dq1210");
        tk.put("domain", "kyfw.12306.cn");
        tk.put("path", "/otn");


        List<Map<String, String>> listCookieMap = new ArrayList<Map<String, String>>();

        listCookieMap.add(BIGipServerotn);
        listCookieMap.add(BIGipServerpool_passport);
        listCookieMap.add(JSESSIONID);
        listCookieMap.add(RAIL_DEVICEID);
        listCookieMap.add(RAIL_EXPIRATION);
        listCookieMap.add(route);
        listCookieMap.add(tk);


        Map<String, Object> proxyMap = new LinkedHashMap<String, Object>();
        proxyMap.put("userName", "hcpkuyou19e");
        proxyMap.put("passWord", "hcpkuyou19e");
        proxyMap.put("ip", "127.0.0.1");
        proxyMap.put("port", 8888);

     /* proxyMap.put("ip", "121.40.170.167");
        proxyMap.put("port", 808);*/

        KyfwClient kyfwClient = null;

        kyfwClient = new KyfwClient(listCookieMap);

        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put("login_site", "E");
        paramMap.put("module", "login");
        paramMap.put("rand", "sjrand");
        StringBuilder paramStr = new StringBuilder();
        paramStr.append(kyfwClient.getUrlParams(paramMap)).append("&").append(Math.random());

        Map<String, String> headMap = new LinkedHashMap<>();
        headMap.put("Host", "kyfw.12306.cn");
        headMap.put("Connection", "keep-alive");
        headMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        headMap.put("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        headMap.put("Referer", "https://kyfw.12306.cn/otn/login/init");
        headMap.put("Accept-Encoding", "gzip, deflate, br");
        headMap.put("Accept-Language", "zh-CN,zh;q=0.9");

        String url = "https://kyfw.12306.cn/passport/captcha/captcha-image";
        byte[] str = kyfwClient.get_downloadFile(url, paramStr.toString(), headMap);
        return str;
    }

    /**
     * 为httpclient设置重试信息
     *
     * @param retryTimes
     */
    public HttpRequestRetryHandler getRetryHandler(final int retryTimes) {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount > retryTimes) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                return idempotent;
            }
        };

        return myRetryHandler;
    }

    /**
     * @param url
     * @param postParams
     * @param headerMap
     * @return
     */
    public String post(String url, Map<String, String> postParams, Map<String, String> headerMap) {

        String result = "";
        HttpPost httpPost = getHttpPost(url);
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
            //httpPost.addHeader("Cookie","");
        }
        List<NameValuePair> paramList = this.getListNameValuePair(postParams);
        if (paramList.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(paramList, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.info("UnsupportedEncodingException:", e);
            }
            httpPost.setEntity(entity);
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("code:{}", statusCode);
            Header[] headers = response.getAllHeaders();
            for (Header h : headers) {
                log.info("getAllHeaders:{}---{}", h.getName(), h.getValue());
            }
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("IOException:", e);
                }
            }

        }

        return result;

    }

    public String post(String url, Map<String, String> postParams) {

        String result = "";
        HttpPost httpPost = getHttpPost(url);

        List<NameValuePair> paramList = this.getListNameValuePair(postParams);

        if (paramList.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(paramList, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.info("UnsupportedEncodingException:", e);
            }
            httpPost.setEntity(entity);
        }

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("code:{}", statusCode);
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("IOException:", e);
                }
            }

        }

        return result;

    }


    private String getResult(CloseableHttpResponse httpResponse, String charset) throws ParseException, IOException {
        String result = null;
        if (httpResponse == null) {
            return result;
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        log.info("code:{}", statusCode);
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return result;
        }
        result = EntityUtils.toString(entity, charset);
        EntityUtils.consume(entity);// 关闭应该关闭的资源，适当的释放资源 ;也可以把底层的流给关闭了
        return result;
    }

    public String get(String url, String params) {
        String result = "";
        StringBuilder urlParam = new StringBuilder();
        if (!StringUtils.isEmpty(params)) {
            urlParam.append(url).append("?").append(params);
        } else {
            urlParam.append(url);
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(getHttpGet(urlParam.toString()));
            result = getResult(response, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:", e);
        }
        return result;
    }

    public String get(String url, String params, Map<String, String> headerMap) {

        String result = "";
        StringBuilder urlParam = new StringBuilder();
        if (!StringUtils.isEmpty(params)) {
            urlParam.append(url).append("?").append(params);
        } else {
            urlParam.append(url);
        }
        CloseableHttpResponse response = null;
        HttpGet httpGet = getHttpGet(urlParam.toString());

        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }

        try {
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("code:{}", statusCode);
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("IOException:", e);
                }
            }

        }

        return result;
    }

    //返回一个map集合
    public Map<String, String> get_resultMap(String url, String params, Map<String, String> headerMap) {

        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        String result = "";
        StringBuilder urlParam = new StringBuilder();
        urlParam.append(url).append("?").append(params);
        CloseableHttpResponse response = null;
        HttpGet httpGet = getHttpGet(urlParam.toString());

        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }

        try {
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            resultMap.put("code", String.valueOf(statusCode));
            log.info("code:{}", statusCode);
            Header[] headers = response.getAllHeaders();
            for (Header h : headers) {
                log.info("getAllHeaders:{}---{}", h.getName(), h.getValue());
                resultMap.put(h.getName(), h.getValue());
            }
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("IOException:", e);
                }
            }

            resultMap.put("result", result);

        }

        return resultMap;
    }

    //返回一个map集合
    public Map<String, String> post_resultMap(String url, Map<String, String> postParams, Map<String, String> headerMap) {
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        String result = "";
        HttpPost httpPost = getHttpPost(url);
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        List<NameValuePair> paramList = this.getListNameValuePair(postParams);
        if (paramList.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(paramList, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.info("UnsupportedEncodingException:", e);
            }
            httpPost.setEntity(entity);
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            resultMap.put("code", String.valueOf(statusCode));
            log.info("code:{}", statusCode);
            Header[] headers = response.getAllHeaders();
            for (Header h : headers) {
                log.info("getAllHeaders:{}---{}", h.getName(), h.getValue());
                resultMap.put(h.getName(), h.getValue());
            }
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("IOException:", e);
                }
            }
            resultMap.put("result", result);
        }
        return resultMap;
    }

    //下载文件
    public byte[] get_downloadFile(String url, String params, Map<String, String> headerMap) {
        String result = "";
        StringBuilder urlParam = new StringBuilder();
        urlParam.append(url).append("?").append(params);
        CloseableHttpResponse response = null;
        HttpGet httpGet = getHttpGet(urlParam.toString());
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        InputStream inStream = null;
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();//输出字节数组
        byte[] buff = new byte[1024];
        byte[] in2b = null;
        try {
            response = client.execute(getHttpGet(urlParam.toString()));
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("code:{}", statusCode);
            HttpEntity entity = response.getEntity();
            inStream = entity.getContent();
            int rc = 0;
            while ((rc = inStream.read(buff, 0, 1024)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            in2b = swapStream.toByteArray();
            EntityUtils.consume(entity);//关闭流
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:", e);
        }
        return in2b;
    }

    //上传文件
    public String post_uploadImage(String url, Map<String, String> postParams, byte[] fileByte) {
        String result = "";
        CloseableHttpResponse response = null;
        HttpPost httpPost = getHttpPost(url);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.addBinaryBody("pic1", fileByte, ContentType.create("image/gif"), String.valueOf(String.valueOf(System.currentTimeMillis()) + new Random().nextInt(10000)) + ".gif");
        for (Map.Entry<String, String> entry : postParams.entrySet()) {
            mEntityBuilder.addTextBody(entry.getKey(), entry.getValue());
        }
        httpPost.setEntity(mEntityBuilder.build());
        try {
            response = client.execute(httpPost);
            result = getResult(response, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:", e);
        }
        return result;
    }


    //post数据为JSON
    public String post_json(String url, String bodyJson) {
        String result = "";
        CloseableHttpResponse response = null;
        HttpPost httpPost = getHttpPost(url);
        try {
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setEntity(new StringEntity(bodyJson, "UTF-8"));

            response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("code:{}", statusCode);
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException:{}", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("IOException:{}", e);
                }
            }

        }

        return result;

    }


    public RequestConfig getRequestConfig(RequestConfig.Builder builder) {
        RequestConfig requestConfig = builder.build();
        return requestConfig;
    }

    public RequestConfig.Builder getRequestConfigBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectionRequestTimeout(30000);//设置从connect Manager获取Connection 超时时间，单位毫秒
        builder.setConnectTimeout(30000);//设置连接超时时间，单位毫秒
        builder.setSocketTimeout(30000);//请求获取数据的超时时间，单位毫秒
        return builder;
    }

    public RequestConfig.Builder getProxyRequestConfigBuilder(Map<String, Object> proxyMap) {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectionRequestTimeout(30000);//设置从connect Manager获取Connection 超时时间，单位毫秒
        builder.setConnectTimeout(30000); //设置连接超时时间，单位毫秒
        builder.setSocketTimeout(30000); //请求获取数据的超时时间，单位毫秒
        HttpHost proxy = this.getHttpHost((String) proxyMap.get("ip"), (Integer) proxyMap.get("port"));
        builder.setProxy(proxy);
        return builder;
    }


    public CloseableHttpClient getHttpClient(HttpClientBuilder httpClientBuilder) {
        CloseableHttpClient client = httpClientBuilder.build();
        return client;
    }

    //基本客户端建造
    public HttpClientBuilder getHttpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(this.getPoolingHttpClientConnectionManager());
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setRetryHandler(this.getRetryHandler(0));//不自动重发
        httpClientBuilder.disableAutomaticRetries();//关闭重定向策略
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        return httpClientBuilder;
    }

    //proxy客户端建造
    public HttpClientBuilder getProxyHttpClientBuilder(Map<String, Object> proxyMap) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope((String) proxyMap.get("ip"), (Integer) proxyMap.get("port")),
                new UsernamePasswordCredentials((String) proxyMap.get("userName"), (String) proxyMap.get("passWord")));
        httpClientBuilder.setConnectionManager(this.getPoolingHttpClientConnectionManager());

        httpClientBuilder.setDefaultCredentialsProvider(credsProvider);

        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setRetryHandler(this.getRetryHandler(0));//不自动重发
        HttpHost proxy = this.getHttpHost((String) proxyMap.get("ip"), (Integer) proxyMap.get("port"));
        httpClientBuilder.setProxy(proxy);
        httpClientBuilder.disableAutomaticRetries();//关闭重定向策略
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        httpClientBuilder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        return httpClientBuilder;
    }

    //cookie客户端建造
    public HttpClientBuilder getCookieHttpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(this.getPoolingHttpClientConnectionManager());
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setRetryHandler(this.getRetryHandler(0));
        httpClientBuilder.disableAutomaticRetries();//关闭重定向策略
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        httpClientBuilder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        return httpClientBuilder;
    }

    //cookie和proxy客户端建造
    public HttpClientBuilder getCookieProxyHttpClientBuilder(Map<String, Object> proxyMap) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(this.getPoolingHttpClientConnectionManager());
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope((String) proxyMap.get("ip"), (Integer) proxyMap.get("port")),
                new UsernamePasswordCredentials((String) proxyMap.get("userName"), (String) proxyMap.get("passWord")));
        httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setRetryHandler(this.getRetryHandler(0));
        HttpHost proxy = this.getHttpHost((String) proxyMap.get("ip"), (Integer) proxyMap.get("port"));
        httpClientBuilder.setProxy(proxy);
        httpClientBuilder.disableAutomaticRetries();//关闭重定向策略
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        httpClientBuilder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        return httpClientBuilder;
    }


    public HttpGet getHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        return httpGet;
    }

    public HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        return httpPost;
    }

    public SSLContext getSSLContext() {
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSLv3");
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sc.init(null, new TrustManager[]{trustManager}, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sc;
    }

    public Registry<ConnectionSocketFactory> getSocketFactoryRegistry() {

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        registryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);
        registryBuilder.register("https", new SSLConnectionSocketFactory(this.getSSLContext()));

        return registryBuilder.build();
    }

    public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(this.getSocketFactoryRegistry());
        return connManager;
    }

    public List<NameValuePair> getListNameValuePair(Map<String, String> params) {
        List<NameValuePair> ps = new ArrayList<NameValuePair>();
        if (null != params && !params.isEmpty()) {
            for (String pKey : params.keySet()) {
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));
            }
        }
        return ps;
    }

    public String getUrlParams(Map<String, String> params) {

        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                param.append(key).append("=").append(URLEncoder.encode(params.get(key), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (i != params.size() - 1) {
                param.append("&");
            }
            i++;
        }

        return param.toString();
    }

    public HttpHost getHttpHost(String ip, int port) {
        HttpHost proxy = new HttpHost(ip, port);
        return proxy;

    }

    public CookieStore getCookieStore(List<BasicClientCookie> basicClientCookieList) {
        CookieStore cookieStore = new BasicCookieStore();
        for (BasicClientCookie basicClientCookie : basicClientCookieList) {
            cookieStore.addCookie(basicClientCookie);
        }
        return cookieStore;
    }


    public List<BasicClientCookie> getBasicClientCookieList(List<Map<String, String>> cookieMapList) {
        ArrayList<BasicClientCookie> basicClientCookieList = new ArrayList<BasicClientCookie>();
        for (Map<String, String> cookieMap : cookieMapList) {
            basicClientCookieList.add(getBasicClientCookie(cookieMap));
        }
        return basicClientCookieList;
    }


    public BasicClientCookie getBasicClientCookie(Map<String, String> cookieMap) {
        BasicClientCookie cookie = new BasicClientCookie(cookieMap.get("name"), cookieMap.get("value"));
        cookie.setVersion(0);
        cookie.setDomain(cookieMap.get("domain"));   //设置范围
        cookie.setPath(cookieMap.get("path"));
        return cookie;
    }

    public BasicCookieStore getCookieStore() {
        return (BasicCookieStore) cookieStore;
    }
}
