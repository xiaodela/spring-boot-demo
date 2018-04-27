package com.l9e.transaction.bean;

import java.io.Serializable;

/**
 * @author meizs
 * @create 2018-04-19 14:17
 **/
public class Tutorial implements Serializable {
    private Long id;
    private String name;//教程名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tutorial{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}