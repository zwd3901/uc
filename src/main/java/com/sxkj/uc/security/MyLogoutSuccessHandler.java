package com.sxkj.uc.security;

import com.google.gson.Gson;
import com.sxkj.common.response.MyResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyLogoutSuccessHandler
 * @Description: TODO  退出
 * @Author zwd
 * @Date 2019/12/26 0026
 **/
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.getWriter().write(new Gson().toJson(MyResponseUtil.success()));
    }
}
