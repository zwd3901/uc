package com.sxkj.uc.api;

import com.sxkj.common.params.ResponseEnum;
import com.sxkj.common.response.MyResponse;
import com.sxkj.common.response.MyResponseUtil;
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
    public MyResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof UnauthorizedException) {
            return MyResponseUtil.fail(ResponseEnum.UN_AUTHORIZATION);
        }
        if (e instanceof ExpiredCredentialsException) {
            return MyResponseUtil.fail(ResponseEnum.TOKEN_ERROR);
        }
        if (e instanceof IncorrectCredentialsException) {
            return MyResponseUtil.fail(ResponseEnum.TOKEN_ERROR);
        }
        if (e instanceof AuthenticationException) {
            return MyResponseUtil.fail(ResponseEnum.UN_AUTHENTICATION);
        }
        return MyResponseUtil.fail(ResponseEnum.SERVER_EXCEPTION);
    }
}
