package com.sxkj.uc.interceptor;

import com.sxkj.uc.entity.Token;
import com.sxkj.uc.service.TokenService;
import com.sxkj.uc.util.AppContext;
import com.sxkj.uc.util.code.CustomResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String token = AppContext.getToken(request);
        // 获取token对象
        Token sysToken = null;

        if (token != null && !"".equals(token)) {
            sysToken = tokenService.findByToken(token);
        }
        boolean flag = tokenCheck(sysToken,token);
        if (flag) {
            // 更新token
            tokenService.updateToken(sysToken);
            return true;
        }else {
            throw new RuntimeException(CustomResultCodeEnum.TOKEN_EXPIRE.getCode());
        }
    }

    private boolean tokenCheck(Token sysToken, String token){
        if (token == null || "".equals(token)) {
            log.info("token检查，没有token");
            return false;
        }
        if (sysToken == null) {
            log.info("token检查，没有找到Token对象");
            return false;
        }
        // 判断token是否过期
        long now = System.currentTimeMillis();
        long expireTime = sysToken.getExpireTime();
        if(expireTime-now<=0){
            log.info("token检查，已过期");
            return false;
        }
        return true;
    }
}
