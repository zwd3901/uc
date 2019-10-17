package com.sxkj.uc.auth;

import com.sxkj.uc.jwt.JwtConfig;
import com.sxkj.uc.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    @Value("${config.jwt.secret}")
    private String secret;
    @Value("${config.jwt.expire}")
    private long expire;
    @Value("${config.jwt.header}")
    private String header;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        System.out.println("getContextPath:" + request.getContextPath());
        System.out.println("getServletPath:" + request.getServletPath());
        System.out.println("getRequestURI:" + request.getRequestURI());
        System.out.println("getRequestURL:" + request.getRequestURL());
        System.out.println("getRealPath:" + request.getSession().getServletContext().getRealPath("image"));
        /*String token = request.getHeader(header);
        if (token == null || "".equals(token)) {
            token = request.getParameter(header);
        }
        if (token == null || "".equals(token)) {
            throw new RuntimeException(header + "不能为空");
        }
        Claims claims = jwtConfig.getTokenClaims(token);
        if (claims == null || jwtConfig.isExpired(claims.getExpiration())) {
            throw new RuntimeException(header + "失效，请重新登录");
        } else {
            System.err.println(jwtConfig.refreshToken(token));
        }

        request.setAttribute("id", claims.getSubject());*/

        return true;

    }
}
