package com.sxkj.uc.interceptor;

import com.sxkj.uc.entity.Token;
import com.sxkj.uc.service.TokenService;
import com.sxkj.uc.util.AppContext;
import com.sxkj.uc.util.code.CustomResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TokenInterceptor
 * @Description: 验证发起请求的用户是否登录，token是否有效，更新token
 * @Author zwd
 * @Date 2019/12/12 0012
 **/
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;

    /**
     * 获取token，并验证token是否存在，是否超时，同时对合法的token进行更新
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String token = AppContext.getToken(request);
        // 获取token对象
        Token sysToken = null;
        System.err.println("request token : " + token);
        if (token != null && !"".equals(token)) {
            sysToken = tokenService.findByToken(token);
        }
        if (tokenService.tokenCheck(sysToken, token)) {
            // 更新token
            tokenService.updateToken(sysToken);
            return true;
        } else {
            throw new ExpiredCredentialsException(CustomResultCodeEnum.TOKEN_EXPIRE.getCode());
        }
    }


}
