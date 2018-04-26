package com.l9e.common;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author meizs
 * @create 2018-04-10 11:07
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public String allExceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                      Exception exception) throws Exception {
        exception.printStackTrace();

        JSONObject errReturn = new JSONObject();
        logger.info("系统全局错误拦截，错误信息" + exception);
        errReturn.put("return_code", "001");
        errReturn.put("message", "系统错误，未知服务异常。");
        // printJson(response, errReturn.toString());
        return errReturn.toString();
    }

    public static void printJson(HttpServletResponse response, String str) {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(str);
        } catch (IOException e) {
            logger.error("输出异常！", e);
        } finally {
            out.flush();
            out.close();
        }
    }
}
