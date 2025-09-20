package com.sonar.devops.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CentralLogger {
    private static final Logger logger = LoggerFactory.getLogger(CentralLogger.class);

    @Pointcut("execution(* com.sonar.devops.controller..*(..))")
    public void pointcutMethod() {
    }

    @Around("pointcutMethod()")
    public Object logMessages(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        logger.info("Class: {}----Method: {}-------Request Args: {}", className, methodName, objectMapper.writeValueAsString(args));
        Object objectResponse = pjp.proceed();
        logger.info("Class: {}----Method: {}-------Request Args: {}", className, methodName, objectMapper.writeValueAsString(objectResponse));
        return objectResponse;
    }
}
