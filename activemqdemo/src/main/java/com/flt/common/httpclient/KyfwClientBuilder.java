package com.flt.common.httpclient;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author meizs
 * @create 2018-05-10 13:35
 **/
public class KyfwClientBuilder {

    private Proxy proxy;

    private List<Cookie> listCookie;


    protected KyfwClientBuilder() {
        super();
    }

    public static KyfwClientBuilder create() {
        return new KyfwClientBuilder();
    }


    public final KyfwClientBuilder setProxy(final Proxy proxy) {
        this.proxy = proxy;
        return this;
    }


    public final KyfwClientBuilder setCookie(final List<Cookie> listCookie) {
        this.listCookie = listCookie;
        return this;
    }

    public KyfwClient build() {

        if (proxy == null) {//代理为空
            if (listCookie == null) {
                return new KyfwClient();
            } else {
                if (listCookie.size() > 0) {
                    List<Map<String, String>> listCookieMap = cookieDeal();
                    return new KyfwClient(listCookieMap);
                } else {
                    return new KyfwClient();
                }
            }
        } else {

            if (null != proxy.getIp() && null != proxy.getPort()) {//代理非空
                Map<String, Object> proxyMap = proxyDeal();
                if (listCookie == null) {
                    return new KyfwClient(proxyMap);
                } else {
                    if (listCookie.size() > 0) {
                        List<Map<String, String>> listCookieMap = cookieDeal();
                        return new KyfwClient(listCookieMap, proxyMap);
                    } else {
                        return new KyfwClient(proxyMap);
                    }
                }

            } else {

                return new KyfwClient();
            }


        }

        /////*********************//////////////

    }

    public Map<String, Object> proxyDeal() {
        Map<String, Object> proxyMap = new LinkedHashMap<>();
        proxyMap.put("ip", proxy.getIp());
        proxyMap.put("port", Integer.valueOf(proxy.getPort()));
        if (null != proxy.getUserName() && null != proxy.getPassWord()) {
            proxyMap.put("userName", proxy.getUserName());
            proxyMap.put("passWord", proxy.getPassWord());
        } else {
            proxyMap.put("userName", "");
            proxyMap.put("passWord", "");
        }
        return proxyMap;
    }

    public List<Map<String, String>> cookieDeal() {
        List<Map<String, String>> listCookieMap = new LinkedList<>();
        for (Cookie cookie : listCookie) {
            Map<String, String> cookieMap = new LinkedHashMap<>();
            cookieMap.put("name", cookie.getName());
            cookieMap.put("value", cookie.getValue());
            cookieMap.put("domain", cookie.getDomain());
            cookieMap.put("path", cookie.getPath());
            listCookieMap.add(cookieMap);
        }
        return listCookieMap;
    }


}
