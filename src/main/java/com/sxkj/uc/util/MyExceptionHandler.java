package com.sxkj.uc.util;

import com.sxkj.uc.util.code.CustomResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zwd
 * 异常处理
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public CustomResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof UnauthorizedException) {
            return CustomResultUtil.fail(CustomResultCodeEnum.NO_PERMIT);
        }
        if (e instanceof ExpiredCredentialsException) {
            return CustomResultUtil.fail(CustomResultCodeEnum.TOKEN_EXPIRE);
        }
        if (e instanceof IncorrectCredentialsException) {
            return CustomResultUtil.fail(CustomResultCodeEnum.NO_TOKEN);
        }
        if (e instanceof AuthenticationException) {
            return CustomResultUtil.fail(CustomResultCodeEnum.LOG_IN_NO);
        }
        return CustomResultUtil.fail(CustomResultCodeEnum.EXCEPTION.getCode(), e.getCause().toString());
    }
}
