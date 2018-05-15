package com.flt.commom.kyfwconst;

/**
 * @author meizs
 * @create 2018-05-07 14:05
 **/
public enum HeaderType {

    UserAgent("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36"),
    AcceptEncoding("Accept-Encoding", "gzip, deflate, br"),
    AcceptLanguage("Accept-Language", "zh-CN,zh;q=0.9");


    private String name;
    private String value;

    HeaderType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
