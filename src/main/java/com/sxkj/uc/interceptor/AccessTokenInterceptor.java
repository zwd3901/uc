package com.sxkj.uc.interceptor;

import com.sxkj.common.util.AppContext;
import com.sxkj.common.util.code.MyResponseStatusEnum;
import com.sxkj.uc.entity.OnLine;
import com.sxkj.uc.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AccessTokenInterceptor
 * @Description: 验证发起请求的用户是否登录，token是否有效，更新token
 * @Author zwd
 * @Date 2019/12/12 0012
 **/
@Slf4j
public class AccessTokenInterceptor extends HandlerInterceptorAdapter {
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private AccessTokenService tokenService;

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
        System.err.println(request.getContextPath() + ".................................");
        // 获取token
        String token = AppContext.getToken(request);
        // 获取token对象
        OnLine onLine = null;
        System.err.println("request token : " + token);
        if (token != null && !"".equals(token)) {
            onLine = tokenService.findByToken(token);
        }
        if (tokenService.tokenCheck(onLine, token)) {
            String secret = AppContext.getSecretKey(request);

            // 更新token
            tokenService.updateToken(onLine);
            return true;
        } else {
            throw new ExpiredCredentialsException(MyResponseStatusEnum.TOKEN_ERROR.getCode() + "");
        }
    }


}
