package com.sxkj.uc.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zwd
 */
public class AppContext {
    private static final String AUTH_KEY = "token";
    private static final String SECRET_KEY = "secret_id";

    private static ServletRequestAttributes getRequestAttributes() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes;
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取请求的token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTH_KEY);
        if (token == null || "".equals(token)) {
            token = request.getParameter(AUTH_KEY);
        }
        return token == null ? "" : token;
    }

    public static String getToken() {
        return getToken(getRequestAttributes().getRequest());
    }

    public static String getSecretKey(HttpServletRequest request) {
        String secret = request.getHeader(SECRET_KEY);
        if (secret == null || "".equals(secret)) {
            secret = request.getParameter(SECRET_KEY);
        }
        return secret == null ? "" : secret;
    }

    public static String getSecretKey() {
        return getSecretKey(getRequestAttributes().getRequest());
    }
}
