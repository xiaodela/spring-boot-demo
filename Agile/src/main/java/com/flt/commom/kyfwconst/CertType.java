package com.flt.commom.kyfwconst;

/**
 * @author meizs
 * @create 2018-05-07 11:24
 **/
public enum CertType {

    yd("一代身份证", "2", "1"),
    ed("二代身份证", "1", "2"),
    ga("港澳通行证", "C", "3"),
    tw("台湾通行证", "G", "4"),
    hz("护照", "B", "5");

    // 成员变量
    private String name;
    private String value_12306;
    private String value_19e;

    CertType(String name, String value_12306, String value_19e) {
        this.name = name;
        this.value_12306 = value_12306;
        this.value_19e = value_19e;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue_12306() {
        return value_12306;
    }

    public void setValue_12306(String value_12306) {
        this.value_12306 = value_12306;
    }

    public String getValue_19e() {
        return value_19e;
    }

    public void setValue_19e(String value_19e) {
        this.value_19e = value_19e;
    }
}
