package com.sonar.devops.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceEvaluator {

   private static final Logger logger = LoggerFactory.getLogger(PerformanceEvaluator.class);

@Around("@annotation(com.sonar.devops.aop.TimeDuration)")
public Object methodPerformanceEvaluator(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    String className = proceedingJoinPoint.getTarget().getClass().getName();
    String methodName = proceedingJoinPoint.getSignature().getName();
    Long startTime = System.currentTimeMillis();
    Object objectResponse = proceedingJoinPoint.proceed();
    Long endTime = System.currentTimeMillis();
    logger.info(" Time taken by Class: {}----Method: {}-------is: {}", className, methodName,(endTime-startTime));
    return  objectResponse;
}
}
