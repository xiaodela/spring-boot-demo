import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flt.commom.httpclient.KyfwClient;
import com.flt.commom.httpclient.KyfwClientBuilder;
import com.flt.commom.httpclient.Proxy;
import com.flt.commom.kyfwconst.CertType;
import com.flt.commom.kyfwconst.HeaderType;
import com.flt.commom.kyfwconst.SeatType;
import com.flt.commom.kyfwconst.TicketType;
import com.flt.util.CloneUtils;
import com.flt.util.RedisUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author meizs
 * @create 2018-05-07 10:50
 **/
public class KyfwBookTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private KyfwClient kyfwClient;

    public KyfwBookTest() {

        Proxy proxy = new Proxy("127.0.0.1", "8888", "hcpkuyou19e", "hcpkuyou19e");
        com.flt.commom.httpclient.Cookie RAIL_DEVICEID = new com.flt.commom.httpclient.Cookie("RAIL_DEVICEID", "mAZyKnwdBAor-PjZuBIWaFBlte2DqNNAHYbtKljgLBZOr1rOxyBMh_AjImS2rUFYg0kxJ8wk6yl3Xu85925zJ7NRR1hyX0xv-sdKWo_c-PSQsHQ4ZNZsP1gMVwah37nzA8kcUJYfKgx9Jyx9qq-c-KKC1v_Y3UQ0", "kyfw.12306.cn", "/");
        com.flt.commom.httpclient.Cookie RAIL_EXPIRATION = new com.flt.commom.httpclient.Cookie("RAIL_EXPIRATION", "1525611099381", "kyfw.12306.cn", "/");
        List<com.flt.commom.httpclient.Cookie> listCookie = new LinkedList<>();
        listCookie.add(RAIL_DEVICEID);
        listCookie.add(RAIL_EXPIRATION);

        KyfwClientBuilder kyfwClientBuilder = KyfwClientBuilder.create();
        kyfwClientBuilder
                .setCookie(listCookie)
                .setProxy(proxy);

        KyfwClient kyfwClient = kyfwClientBuilder.build();

        this.kyfwClient = kyfwClient;
    }

    public static void main(String[] args) {

        KyfwBookTest kbt = new KyfwBookTest();
        HashMap<String, Object> bookMap = new LinkedHashMap<>();
        bookMap.put("order_id", "tn1");//订单号
        bookMap.put("userName", "13051979731");//用户名
        bookMap.put("userPwd", "meizs_2015");//密码
        bookMap.put("train_date", "2018-05-30");//出发日期
        bookMap.put("from_station", "北京");//出发站
        bookMap.put("arrive_station", "上海");//到达站
        bookMap.put("train_code", "G151");//车次代码

        List<Map<String, String>> passengerList = new LinkedList<>();
        Map<String, String> passengerMap = new LinkedHashMap<>();
        passengerMap.put("cp_id", "1");//车票号
        passengerMap.put("user_name", "梅志胜");//证件姓名
        passengerMap.put("cert_no", "421182199110152233");//证件号
        passengerMap.put("cert_type", CertType.ed.getValue_12306());//证件类型
        passengerMap.put("ticket_type", TicketType.cr.getValue_12306());//票类型
        passengerMap.put("seat_type", SeatType.rz2.getValue_12306());//坐席类型

        Map<String, String> passengerMap_1 = new LinkedHashMap<>();
        passengerMap_1.put("cp_id", "2");//车票号
        passengerMap_1.put("user_name", "梅志胜");//证件姓名
        passengerMap_1.put("cert_no", "421182199110152233");//证件号
        passengerMap_1.put("cert_type", CertType.ed.getValue_12306());//证件类型
        passengerMap_1.put("ticket_type", TicketType.et.getValue_12306());//票类型
        passengerMap_1.put("seat_type", SeatType.rz2.getValue_12306());//坐席类型

        passengerList.add(passengerMap);
        passengerList.add(passengerMap_1);

        bookMap.put("tickets", passengerList);

        Map<String, Object> resultMap = CloneUtils.clone(bookMap);

        String key = "12306_PC" + bookMap.get("userName") + "_" + bookMap.get("userPwd");
        CookieStore cookieStore = kbt.kyfwClient.getCookieStore();
        String cookieStr = RedisUtil.getInstance().strings().get(key);
        if (!StringUtils.isEmpty(cookieStr)) {
            kbt.getCache12306Cookie(cookieStr, cookieStore);//获取12306cookie，校验是否失效
            String verify_login = kbt.getInitMy12306();
            if ("success".equals(verify_login)) {
                System.out.println("登录成功");
            } else {
                System.out.println("重新登录");
                String result_loginNew = kbt.login_new(bookMap);
                if (!"success".equals(result_loginNew)) {
                    resultMap.put("status", false);
                    resultMap.put("message", result_loginNew);
                    return;
                } else {
                    System.out.println("缓存cookie");
                    kbt.setCache12306Cookie(key, cookieStore);//缓存12306Cookie到redis
                }
            }

        } else {

            System.out.println("重新登录");
            String result_loginNew = kbt.login_new(bookMap);

            if (!"success".equals(result_loginNew)) {
                resultMap.put("status", false);
                resultMap.put("message", result_loginNew);
                return;
            } else {
                System.out.println("缓存cookie");
                kbt.setCache12306Cookie(key, cookieStore);//缓存12306Cookie到redis
            }


        }

        System.out.println("——————————————————————————开始其它操作——————————————————————————");
        kbt.book();
        System.out.println(JSON.toJSONString(resultMap));
        return;
    }

    public void book() {

        System.out.println(queryOrderNoComplete());
    }


    public String queryOrderNoComplete() {
        String url = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrderNoComplete";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "*/*");
        headerMap.put("Cache-Control", "max-age=0");
        headerMap.put("Origin", "https://kyfw.12306.cn");
        headerMap.put("X-Requested-With", "XMLHttpRequest");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/queryOrder/initNoComplete");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());
        Map<String, String> postParam = new LinkedHashMap<>();
        postParam.put("_json_att", "");
        String result = kyfwClient.post(url, postParam, headerMap);
        return result;
    }


    public void setCache12306Cookie(String key, CookieStore cookieStore) {
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
        System.out.println(bstr.toString());

        RedisUtil.getInstance().strings().setEx(key, 60 * 20, bstr.toString());
    }

    public void getCache12306Cookie(String cookieStr, CookieStore cookieStore) {
        List<Map<String, String>> cookieMapList = new LinkedList<>();
        String[] cookiesArray = cookieStr.split(";");
        for (String cookie : cookiesArray) {
            String[] cookieAttr = cookie.split(",");
            BasicClientCookie cookie_single = new BasicClientCookie(cookieAttr[0], cookieAttr[1]);
            cookie_single.setDomain(cookieAttr[2]);
            cookie_single.setPath(cookieAttr[3]);
            cookieStore.addCookie(cookie_single);
        }
    }

    public String initMy12306() {

        String url = "https://kyfw.12306.cn/otn/index/initMy12306";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cache-Control", "max-age=0");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Upgrade-Insecure-Requests", "1");
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());

        String result = kyfwClient.get(url, null, headerMap);

        return result;

    }


    public String login_new(Map<String, Object> bookMap) {

        getLoginIndex();
        String result_checkCode = checkCode(bookMap);//打码三次失败，直接结束
        logger.info(result_checkCode);
        if (!"success".equals(result_checkCode)) {
            return result_checkCode;
        }
        String result_webLogin = webLogin(bookMap);//检验用户名密码
        logger.info(result_webLogin);
        if (!"success".equals(result_webLogin)) {
            return result_webLogin;
        }


        String result = userLogin1();
        // logger.info(result);
        String newapptk = authUamtk();
        logger.info(newapptk);
        if (!StringUtils.isEmpty(newapptk)) {
            String result_uamauthclient = uamauthclient(newapptk);
            logger.info(result_uamauthclient);
            if (!"success".equals(result_uamauthclient)) {
                return result_uamauthclient;
            }
        } else {
            return "authUamtk获取失败";
        }
        String userLogin2 = userLogin2();//重定向
        //<a id="login_user" href="/otn/index/initMy12306" class="colorA" style="margin-left:-0.5px;"><span style="width:50px;">梅志胜</span>
        return getInitMy12306();

    }

    public String getInitMy12306() {
        String initMy12306_result = initMy12306();//验证是否登录成功
        Document userLogin2_doc = Jsoup.parse(initMy12306_result);
        Element login_user_element = userLogin2_doc.getElementById("login_user");
        logger.info(login_user_element.attr("href"));
        if ("/otn/index/initMy12306".equals(login_user_element.attr("href"))) {
            logger.info("登录成功");
            return "success";
        } else {
            logger.info("未登录成功");
            return "initMy12306失败";
        }
    }


    public String authUamtk() {

        String url = "https://kyfw.12306.cn/passport/web/auth/uamtk";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headerMap.put("Origin", "https://kyfw.12306.cn");
        headerMap.put("X-Requested-With", "XMLHttpRequest");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/passport?redirect=/otn/login/userLogin");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());
        Map<String, String> postParam = new LinkedHashMap<>();
        postParam.put("appid", "otn");
        String result = "";
        String result_code = "";
        String newapptk = "";
        String result_message = "";

        for (int i = 0; i < 3; i++) {
            try {
                result = kyfwClient.post(url, postParam, headerMap);
                //{"result_message":"验证通过","result_code":0,"apptk":null,"newapptk":"DYz3EPaWPr4XloT9LO7Q9X-haiUyLcLYECRLCHc58t4cg1210"}
                logger.info(result);
                result_code = String.valueOf(JSON.parseObject(result).get("result_code"));
                result_message = JSON.parseObject(result).getString("result_message");
                newapptk = JSON.parseObject(result).getString("newapptk");
                if ("0".equals(result_code)) {
                    return newapptk;
                } else {
                    sleep(1);
                }
            } catch (Exception e) {
                logger.info("e", e);
                sleep(1);
            }
        }
        return newapptk;
    }

    public String uamauthclient(String tk) {
        String url = "https://kyfw.12306.cn/otn/uamauthclient";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "*/*");
        headerMap.put("Origin", "https://kyfw.12306.cn");
        headerMap.put("X-Requested-With", "XMLHttpRequest");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/passport?redirect=/otn/login/userLogin");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());
        Map<String, String> postParam = new LinkedHashMap<>();
        postParam.put("tk", tk);

        String result = "";
        String result_code = "";
        String apptk = "";
        String result_message = "";
        for (int i = 0; i < 3; i++) {
            try {
                result = kyfwClient.post(url, postParam, headerMap);
                //{"apptk":"DYz3EPaWPr4XloT9LO7Q9X-haiUyLcLYECRLCHc58t4cg1210","result_code":0,"result_message":"验证通过","username":"梅志胜"}
                logger.info(result);
                result_code = String.valueOf(JSON.parseObject(result).getInteger("result_code"));
                apptk = JSON.parseObject(result).getString("apptk");
                result_message = JSON.parseObject(result).getString("result_message");
                if ("0".equals(result_code)) {
                    result = "success";
                    return result;
                }
            } catch (Exception e) {
                logger.info("e", e);
            }
        }

        return result_message;
    }


    public String userLogin1() {

        String url = "https://kyfw.12306.cn/otn/login/userLogin";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cache-Control", "max-age=0");
        headerMap.put("Origin", "https://kyfw.12306.cn");
        headerMap.put("Upgrade-Insecure-Requests", "1");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/login/init");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());
        Map<String, String> postParam = new LinkedHashMap<>();
        postParam.put("_json_att", "");

        String result = kyfwClient.post(url, postParam, headerMap);

        return result;

    }

    public String userLogin2() {
        String url = "https://kyfw.12306.cn/otn/login/userLogin";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Upgrade-Insecure-Requests", "1");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/login/init");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());
        String result = "";
        for (int i = 0; i < 3; i++) {
            result = kyfwClient.post(url, null, headerMap);
            if (!StringUtils.isEmpty(result)) {
                break;
            } else {
                sleep(1);
            }
        }

        return result;
    }

    //校验用户名和密码
    public String webLogin(Map<String, Object> bookMap) {

        String url = "https://kyfw.12306.cn/passport/web/login";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headerMap.put("Origin", "https://kyfw.12306.cn");
        headerMap.put("X-Requested-With", "XMLHttpRequest");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/login/init");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());
        Map<String, String> postParamMap = new LinkedHashMap<>();
        postParamMap.put("username", (String) bookMap.get("userName"));
        postParamMap.put("password", (String) bookMap.get("userPwd"));
        postParamMap.put("appid", "otn");

        String result = "";
        String result_code = "";
        String result_message = "";
        String uamtk = "";
        for (int i = 0; i < 3; i++) {
            try {
                //{"result_message":"登录成功","result_code":0,"uamtk":"cnZMkqAk9syW1r4xPL63iz9IqHy2dLesxyQomQGtRsgqr1210"}
                result = kyfwClient.post(url, postParamMap, headerMap);
                logger.info("result", result);
                result_code = String.valueOf(JSON.parseObject(result).get("result_code"));
                result_message = (String) JSON.parseObject(result).get("result_message");
                uamtk = (String) JSON.parseObject(result).get("uamtk");
                if ("0".equals(result_code)) {
                    result = "success";
                    return result;
                } else {
                    sleep(1);
                }

            } catch (Exception e) {
                e.printStackTrace();
                sleep(1);
            }
        }

        if ("success".equals(result)) {
            result_message = "登录成功";
        }

        return "webLogin:" + result_message;
    }


    public String getLoginIndex() {

        String url = "https://kyfw.12306.cn/otn/login/init";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cache-Control", "max-age=0");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Upgrade-Insecure-Requests", "1");
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8888");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());

        String result = kyfwClient.get(url, null, headerMap);

        return result;


    }

    /**
     * 核验登录验证码是否正确
     *
     * @param bookMap
     * @return
     */
    public String checkCode(Map<String, Object> bookMap) {
        String result_msg = "";
        for (int i = 0; i < 3; i++) {
            Map<String, String> result = get12306VerifyCode(bookMap);

            String message = result.get("message");
            String code = result.get("code");

            if (!StringUtils.isEmpty(message)) {
                return message;
            }

            result_msg = captchaCheck(code);
            if ("success".equals(result_msg)) {
                return "success";
            } else {
                sleep(1);
            }
        }
        return result_msg;
    }


    public String captchaCheck(String code) {

        String url = "https://kyfw.12306.cn/passport/captcha/captcha-check";
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headerMap.put("Origin", "https://kyfw.12306.cn");
        headerMap.put("X-Requested-With", "XMLHttpRequest");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/login/init");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());

        Map<String, String> postParam = new LinkedHashMap<>();
        postParam.put("answer", code);
        postParam.put("login_site", "E");
        postParam.put("rand", "sjrand");

        //{"result_message":"验证码校验成功","result_code":"4"}

        String result = "";
        outer:
        for (int i = 0; i < 3; i++) {
            result = kyfwClient.post(url, postParam, headerMap);
            logger.info(result);
            String result_code = "";
            String result_message = "";
            try {
                result_code = JSON.parseObject(result).getString("result_code");
                result_message = JSON.parseObject(result).getString("result_message");
            } catch (Exception e) {
                logger.info("e", e);
                continue outer;
            }

            if (!StringUtils.isEmpty(result_code)) {
                if ("4".equals(result_code)) {
                    return "success";
                } else {
                    return result_message;
                }
            }
        }

        return "failure";
    }


    public Map<String, String> get12306VerifyCode(Map<String, Object> bookMap) {

        Map<String, String> result_map = new HashMap<>();

        byte[] imagedata = get12306LoginCaptchaImage();

        if (StringUtils.isEmpty(imagedata)) {
            result_map.put("message", "获取12306验证码失败，IP可能被封！");
            return result_map;
        }

        String result = uploadImage(imagedata, bookMap);

        if (StringUtils.isEmpty(result)) {
            result_map.put("message", "上传验证码失败，检查打码服务！");
            return result_map;
        }

        String pic_id = JSON.parseObject(result).getString("pic_id");
        String code = getImageCode(pic_id);

        if (StringUtils.isEmpty(code)) {
            result_map.put("message", "获取打码结果失败，检查打码服务！");
            return result_map;
        }
        result_map.put("code", code);

        return result_map;
    }


    public byte[] get12306LoginCaptchaImage() {

        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Host", "kyfw.12306.cn");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("User-Agent", HeaderType.UserAgent.getValue());
        headerMap.put("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        headerMap.put("Referer", "https://kyfw.12306.cn/otn/login/init");
        headerMap.put("Accept-Encoding", HeaderType.AcceptEncoding.getValue());
        headerMap.put("Accept-Language", HeaderType.AcceptLanguage.getValue());

        String url = "https://kyfw.12306.cn/passport/captcha/captcha-image";
        Map<String, String> getParam = new LinkedHashMap<>();
        getParam.put("login_site", "E");
        getParam.put("module", "login");
        getParam.put("rand", "sjrand");
        StringBuilder urlParams = new StringBuilder();
        urlParams.append(kyfwClient.getUrlParams(getParam)).append("&").append(Math.random());
        byte[] imageData = null;
        for (int i = 0; i < 3; i++) {
            imageData = kyfwClient.get_downloadFile(url, urlParams.toString(), headerMap);
            if (!StringUtils.isEmpty(imageData) && imageData.length > 10000) {
                break;
            } else {
                logger.info("获取图片失败，可能IP被封！");
                sleep(1);
            }

        }
        return imageData;
    }


    public String uploadImage(byte[] imageData, Map<String, Object> bookMap) {

        KyfwClient httpClient = new KyfwClient();
        String url = "http://219.238.151.222:18010/partner/sendCode.do";
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put("order_id", (String) bookMap.get("order_id"));
        paramMap.put("span_time", "60");
        paramMap.put("partner_id", "19e");
        String result = "";
        for (int i = 0; i < 3; i++) {
            result = kyfwClient.post_uploadImage(url, paramMap, imageData);
            if (!StringUtils.isEmpty(result)) {
                break;
            } else {
                sleep(1);
            }
        }
        return result;
    }


    public String getImageCode(String pic_id) {

        String url = "http://219.238.151.222:18010/partner/requestResult.do";
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put("partner_id", "19e");
        paramMap.put("pic_id", pic_id);

        KyfwClient kyfwClient = new KyfwClient();
        String result = null;
        String imageCode = null;
        for (int i = 0; i < 10; i++) {
            result = kyfwClient.get(url, kyfwClient.getUrlParams(paramMap));
            logger.info(result);
            JSONObject obj = null;
            try {
                obj = JSON.parseObject(result);
                imageCode = obj.getString("verify_code");
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            if (!StringUtils.isEmpty(imageCode)) {
                break;
            } else {
                sleep(2);
            }
        }
        return imageCode;
    }

    public String feedBack(String result_status, String pic_id) {
        String url = "http://219.238.151.222:18010/partner/feedBack.do";
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put("partner_id", "19e");
        paramMap.put("pic_id", pic_id);
        paramMap.put("result_status", result_status);
        KyfwClient kyfwClient = new KyfwClient();
        String result = kyfwClient.get(url, kyfwClient.getUrlParams(paramMap));
        return result;
    }


    public KyfwClient getKyfwClient() {
        return kyfwClient;
    }

    public void setKyfwClient(KyfwClient kyfwClient) {
        this.kyfwClient = kyfwClient;
    }

    public void sleep(int s) {
        try {
            Thread.sleep(s * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
