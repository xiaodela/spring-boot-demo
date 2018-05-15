package com.flt.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author meizs
 * @create 2018-05-15 14:11
 **/
public class LogInterceptor implements HandlerInterceptor {

    private final static String SESSION_TOKEN_KEY = "sessionTokenId";

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        MDC.put(SESSION_TOKEN_KEY, token);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info(MDC.get(SESSION_TOKEN_KEY));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        MDC.remove(SESSION_TOKEN_KEY);
    }
}
