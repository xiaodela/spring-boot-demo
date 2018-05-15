package com.flt.common.httpclient;

/**
 * @author meizs
 * @create 2018-05-10 13:40
 **/
public class Cookie {

    String name;
    String value;
    String domain;
    String path;

    public Cookie(String name, String value, String domain, String path) {
        this.name = name;
        this.value = value;
        this.domain = domain;
        this.path = path;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
