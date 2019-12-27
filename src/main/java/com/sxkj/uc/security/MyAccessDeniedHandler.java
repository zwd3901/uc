package com.sxkj.uc.security;

import com.google.gson.Gson;
import com.sxkj.common.params.ResponseEnum;
import com.sxkj.common.response.MyResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyAccessDeniedHandler
 * @Description: TODO 无权限
 * @Author zwd
 * @Date 2019/12/26 0026
 **/
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write(new Gson().toJson(MyResponseUtil.fail(ResponseEnum.UN_AUTHORIZATION)));
    }
}
