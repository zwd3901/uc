package com.sxkj.uc.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UcContext {

    private ServletRequestAttributes getRequestAttributes(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes;
    }

    public HttpServletRequest getRequest(){
        return getRequestAttributes().getRequest();
    }

    public HttpServletResponse getResponse(){
        return getRequestAttributes().getResponse();
    }
}
