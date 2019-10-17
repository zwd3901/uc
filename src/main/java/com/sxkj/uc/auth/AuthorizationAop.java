package com.sxkj.uc.auth;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zwd
 */
@Aspect
@Slf4j
@Component
public class AuthorizationAop {

    @Pointcut("execution(public * com.sxkj.*.api.*.*(..))")
    public void point(){
        log.info("call log()");
    }

    //@Before("point()")
    public void doBefore(JoinPoint joinPoint) {
        log.error("call doBefore.....");
    }
}
