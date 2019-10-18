package com.sxkj.uc.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public CustomResult handleException(HttpServletRequest request, Exception e) {
        log.error("exception error:{}", e);
        return CustomResultUtil.fail(e.getMessage(),e.getCause().toString());
    }
}
