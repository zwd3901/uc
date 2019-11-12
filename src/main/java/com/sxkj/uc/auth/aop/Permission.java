package com.sxkj.uc.auth.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zwd
 * 权限注解，使用在controller需要添加权限的方法上
 * TODO 此处只为演示用法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    /**
     * 权限名称
     * @return
     */
    String value() ;
}
