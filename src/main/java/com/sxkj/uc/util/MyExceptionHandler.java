package com.sxkj.uc.util;

import com.sxkj.uc.util.code.CustomResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
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
        log.error("exception error:{}", e);
        if (e instanceof UnauthorizedException){
            return CustomResultUtil.fail(CustomResultCodeEnum.NO_PERMIT);
        }

        return CustomResultUtil.fail(CustomResultCodeEnum.EXCEPTION.getCode(),e.getCause().toString());
    }
}
