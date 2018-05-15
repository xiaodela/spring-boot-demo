package com.flt.commom.exception;

/**
 * @author meizs
 * @create 2018-05-02 16:45
 **/
public class CustomException extends Exception {
    private Integer code;

    public CustomException() {
    }

    public CustomException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}