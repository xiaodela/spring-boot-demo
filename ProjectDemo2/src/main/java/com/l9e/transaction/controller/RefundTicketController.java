package com.l9e.transaction.controller;

import com.l9e.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author meizs
 */
@Controller
@RequestMapping("/refund")
public class RefundTicketController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {

    }

    /**
     * 接收body-json数据
     *
     * @param request
     * @param response
     * @param json
     * @return
     */
    @RequestMapping(value = "/queryTicket.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8;")
    @ResponseBody
    public String queryTicket(HttpServletRequest request, HttpServletResponse response, @RequestBody String json) {

        logger.info(json);

        return json;

    }

    /**
     * 异步响应结果，接收get,post,
     *
     * @param request
     * @param response
     */
    @RequestMapping("/refundTicket.do")
    public void refundTicket(HttpServletRequest request, HttpServletResponse response) {

        logger.info("****");

        printJson(response, getJson("000").toString());

        try {
            Thread.sleep(1000 * 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("******");

    }



}
