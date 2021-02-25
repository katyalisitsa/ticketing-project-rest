package com.apis.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAspect {

    @Pointcut("execution(* com.apis.controller.ProjectController.*(..)) || execution(* com.apis.controller.TaskController.*(..))")
    private void anyControllerOperation() {
    }

    
}
