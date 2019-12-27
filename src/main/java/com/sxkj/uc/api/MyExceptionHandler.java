package com.sxkj.uc.api;

import com.sxkj.common.params.ResponseEnum;
import com.sxkj.common.response.MyResponse;
import com.sxkj.common.response.MyResponseUtil;
import lombok.extern.slf4j.Slf4j;
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
        return MyResponseUtil.fail(ResponseEnum.SERVER_EXCEPTION);
    }
}
