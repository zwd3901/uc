package com.sxkj.uc.auth.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zwd
 * 权限注解，使用在controller需要添加权限的方法上
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permit {
    /**
     * 权限名称，这里暂时要求与app的那么对应
     * TODO 待完善
     * @return
     */
    String value() ;
}
