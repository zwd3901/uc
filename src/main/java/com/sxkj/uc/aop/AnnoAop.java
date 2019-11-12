package com.sxkj.uc.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.rmi.ServerError;

@Component
@Aspect
@Slf4j
public class AnnoAop {

    @Pointcut("@annotation(com.sxkj.uc.aop.Anno)")
    public void annoPoint(){}

    @Around(value = "@annotation(com.sxkj.uc.aop.Anno)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("starting ........");

        Object object = joinPoint.proceed();

        return object;
    }

    @Before("annoPoint()")
    public void before(JoinPoint joinPoint) {
        Object[] objects = joinPoint.getArgs();

        System.err.println("this is before .......");
    }
    @After("annoPoint()")
    public void after() {
        System.err.println("this is after ......");
    }


}
