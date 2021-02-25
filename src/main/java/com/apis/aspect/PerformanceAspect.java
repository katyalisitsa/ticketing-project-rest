package com.apis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class PerformanceAspect {


    @Pointcut("@annotation(com.apis.annotation.ExecutionTime)")
    private void anyExecutionTime() {
    }

    @Around("anyExecutionTime()")
    public Object anyExecutionTimeOperationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long beforeTime = System.currentTimeMillis();
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long afterTime = System.currentTimeMillis();

        log.info("Time taken to execute : {} ms (Method : {} - Parameters : {})", (afterTime - beforeTime), proceedingJoinPoint.getSignature().toShortString(), proceedingJoinPoint.getArgs());

        return result;

    }
}
