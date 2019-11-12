package com.sxkj.uc.auth;

import com.sxkj.uc.config.JwtParam;
import com.sxkj.uc.jwt.JwtConfig;
import com.sxkj.uc.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author zwd
 * 许可检查，检查请求的用户是否登录
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private JwtParam jwtParam;


    /**
     * 进入业务方法前执行，可用于认证、授权
     * @param request
     * @param response
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        System.out.println("getContextPath:" + request.getContextPath());
        System.out.println("getServletPath:" + request.getServletPath());
        System.out.println("getRequestURI:" + request.getRequestURI());
        System.out.println("getRequestURL:" + request.getRequestURL());
        System.out.println("getRealPath:" + request.getSession().getServletContext().getRealPath("image"));
        /*Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.err.println(key+" :: "+value);
        }*/
        // 从header中获取token
        String token = request.getHeader(jwtParam.getHeader());
        if (token == null || "".equals(token)) {
            // 从请求参数中获取token
            token = request.getParameter(jwtParam.getHeader());
            // todo 还应该对此进行完善
        }
        if (token == null || "".equals(token)) {
            throw new RuntimeException(jwtParam.getHeader() + "不能为空");
        }
        Claims claims = jwtConfig.getTokenClaims(token);
        if (claims == null || jwtConfig.isExpired(claims.getExpiration())) {
            throw new RuntimeException(jwtParam.getHeader() + "失效，请重新登录");
        } else {
            System.err.println(jwtConfig.refreshToken(token));
        }

        request.setAttribute("id", claims.getSubject());

        return true;

    }
}
