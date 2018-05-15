package com.flt.common.httpclient;

/**
 * @author meizs
 * @create 2018-05-10 13:37
 **/
public class Proxy {

    String ip;
    String port;
    String userName;
    String passWord;

    public Proxy(String ip, String port, String userName, String passWord) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
