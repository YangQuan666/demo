/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package infosec.manager.aspect;

import infosec.manager.annotation.DigestLogger;
import infosec.manager.util.DigestLoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yunzhi
 * @version DigestLoggerAspect.java, v 0.1 2023年02月20日 16:43 yunzhi
 */
@Aspect
@Component
@Slf4j
public class DigestLoggerAspect {

    @Pointcut("@annotation(infosec.manager.annotation.DigestLogger)")
    public void digestLogger() {
    }

    @Around("digestLogger()")
    public Object doAround(ProceedingJoinPoint joinPoint) {

        try {
            Object proceed = null;
            List<Object> args;
            try {
                proceed = joinPoint.proceed();
                args = DigestLoggerUtil.getArgs();
            } catch (Throwable e) {
                args = new ArrayList<>();
                log.warn("execute method exception", e);
            }

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            DigestLogger annotation = AnnotationUtils.findAnnotation(signature.getMethod(), DigestLogger.class);
            if (annotation == null) {
                return proceed;
            }

            LoggerFactory.getLogger(annotation.loggerName()).info(annotation.pattern(), args.toArray());
            return proceed;
        } finally {
            DigestLoggerUtil.clear();
        }
    }
}