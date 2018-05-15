package com.flt.commom.kyfwconst;

/**
 * @author meizs
 * @create 2018-05-07 11:38
 **/
public enum SeatType {

    swz("商务座", "9", "0"),
    tdz("特等座", "P", "1"),
    rz2("二等座", "O", "2"),
    rz1("一等座", "M", "3"),
    yw("硬卧", "3", "6"),
    rw("软卧", "4", "5"),
    wz("无座", "", "9"),
    yz("硬座", "1", "8"),
    rz("软座", "2", "7"),
    gw("高级软卧", "6", "4"),
    dw("动卧", "F", "20"),
    qt("其它", "", "10");

    // 成员变量
    private String name;
    private String value_12306;
    private String value_19e;

    SeatType(String name, String value_12306, String value_19e) {
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
