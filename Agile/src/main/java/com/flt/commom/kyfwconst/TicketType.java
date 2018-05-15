package com.flt.commom.kyfwconst;

/**
 * @author meizs
 * @create 2018-05-07 11:33
 **/
public enum TicketType {

    cr("成人票", "1", "0"),
    et("儿童票", "2", "1"),
    xs("学生票", "3", "3"),
    cj("残军票", "4", "");

    // 成员变量
    private String name;
    private String value_12306;
    private String value_19e;


    TicketType(String name, String value_12306, String value_19e) {
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
