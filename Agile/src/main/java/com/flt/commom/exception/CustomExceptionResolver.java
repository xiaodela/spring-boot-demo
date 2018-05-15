package com.flt.commom.exception;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author meizs
 * @create 2018-05-02 16:44
 **/
@Component("exceptionResolver")
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private static Logger log = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        CustomException customException = null;
        try {
            if (ex instanceof CustomException) {
                customException = (CustomException) ex;
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("msg", customException.getMessage());
                resultMap.put("code", customException.getCode());
                response.getWriter().write(JSON.toJSONString(resultMap));
            } else {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("msg", ex.getMessage());
                resultMap.put("code", -1);
                response.getWriter().write(JSON.toJSONString(resultMap));
            }
        } catch (IOException e) {
            log.info("与客户端通讯异常:" + e.getMessage(), e);
        }
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

}
