package com.sxkj.uc.security;

import com.google.gson.Gson;
import com.sxkj.common.params.ResponseEnum;
import com.sxkj.common.response.MyResponse;
import com.sxkj.common.response.MyResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyAuthenticationEntryPoint
 * @Description: TODO 提醒登录
 * @Author zwd
 * @Date 2019/12/26 0026
 **/
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        MyResponse myResponse = MyResponseUtil.fail(ResponseEnum.UN_AUTHENTICATION, "please login");
        httpServletResponse.getWriter().write(new Gson().toJson(myResponse));
    }
}
