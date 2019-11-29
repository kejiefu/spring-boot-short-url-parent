package com.mountain.url.controller.component;


import com.alibaba.fastjson.JSONObject;
import com.mountain.url.bean.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;


@Component
@Aspect
@Order(1)
@Slf4j
public class ControllerAspect {

    @Around("within(com.mountain.url.controller.*Controller)")
    public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object rt = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("controller call begin...: {}", log(jp));
            }
            try {
                rt = jp.proceed();
            } catch (Throwable t) {
                DataResult response = shouldProcess(jp);
                if (response != null) {
                    return response;
                } else {
                    throw t;
                }
            }
            if (log.isInfoEnabled()) {
                log.info("controller call finish [{}]: {} - {}", (System.currentTimeMillis() - start), log(jp), (rt instanceof InputStream ? "<<InputStream>>" : JSONObject.toJSONString(rt)));
            }
        } catch (Throwable e) {
            if (log.isWarnEnabled()) {
                log.warn("controller call exception [{}]: {}", (System.currentTimeMillis() - start), log(jp), e);
            }
            throw e;
        }
        return rt;
    }

    protected String log(ProceedingJoinPoint jp) {
        return jp.getSignature().getDeclaringType().getSimpleName() + "." + jp.getSignature().getName() + Arrays.toString(jp.getArgs());
    }

    protected DataResult shouldProcess(ProceedingJoinPoint jp) {
        try {
            Method method = jp.getSignature().getClass().getDeclaredMethod("getReturnType", null);
            method.setAccessible(true);
            Class clazz = (Class) method.invoke(jp.getSignature(), null);
            if (DataResult.class.isAssignableFrom(clazz)) {
                return (DataResult) clazz.newInstance();
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("cannot process resource: {} - {}", log(jp), e.getMessage());
            }
        }
        return null;
    }
}
