package com.sxkj.uc.auth.jwt;

import com.sxkj.uc.config.JwtParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zwd
 * 使用request獲取token
 */
@Component
public class JwtUtil {
    @Autowired
    private JwtParam jwtParam;

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * 获取token
     * @return
     */
    public String getToken(){
        return getRequest().getHeader(jwtParam.getHeader());
    }
}
