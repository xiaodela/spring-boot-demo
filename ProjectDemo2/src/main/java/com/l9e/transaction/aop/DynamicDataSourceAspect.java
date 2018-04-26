package com.l9e.transaction.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author meizs
 * @create 2018-04-10 16:32
 **/
/*@Aspect
@Component*/
public class DynamicDataSourceAspect {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Around("@annotation(DS)")
    public Object beforeSwitchDS(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //获得当前访问的class
        Class<?> className = proceedingJoinPoint.getTarget().getClass();

        //获得访问的方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterTypes();
        DatabaseType dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
            DataSourceContextHolder.setDB(dataSource); // 切换数据源
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            DataSourceContextHolder.clearDB();
        }

    }


}

