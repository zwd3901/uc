package com.sxkj.uc.util;

import com.sxkj.common.util.MyResponse;
import com.sxkj.common.util.MyResponseUtil;
import com.sxkj.common.util.code.MyResponseStatusEnum;
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
            return MyResponseUtil.fail(MyResponseStatusEnum.UN_AUTHORIZATION);
        }
        if (e instanceof ExpiredCredentialsException) {
            return MyResponseUtil.fail(MyResponseStatusEnum.TOKEN_ERROR);
        }
        if (e instanceof IncorrectCredentialsException) {
            return MyResponseUtil.fail(MyResponseStatusEnum.TOKEN_ERROR);
        }
        if (e instanceof AuthenticationException) {
            return MyResponseUtil.fail(MyResponseStatusEnum.UN_AUTHENTICATION);
        }
        return MyResponseUtil.fail(MyResponseStatusEnum.SERVER_EXCEPTION, e.getCause().toString());
    }
}
