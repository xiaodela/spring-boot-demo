package com.flt.bl.controller;

import com.flt.bl.dao.EmployeeRepository;
import com.flt.bl.vo.Employee;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2018/4/18 19:49
 */
@RestController
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    private EmployeeRepository er;

    //增加
    @RequestMapping("/add")
    public String add() {

        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("xuxu");
        employee.setLastName("zh");
        employee.setAge(26);
        employee.setAbout("i am in peking");
        er.save(employee);

        System.err.println("add a obj");

        return "success";
    }

    //删除
    @RequestMapping("/delete")
    public String delete() {

        er.delete("1");

        return "success";
    }

    //局部更新
    @RequestMapping("/update")
    public String update() {

        Employee employee = er.queryEmployeeById("1");
        employee.setFirstName("哈哈");
        er.save(employee);

        System.err.println("update a obj");

        return "success";
    }

    //查询
    @RequestMapping("/query")
    public Employee query() {

        Employee accountInfo = er.queryEmployeeById("1");
        System.err.println(new Gson().toJson(accountInfo));

        return accountInfo;
    }

}
