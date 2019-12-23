package com.sxkj.uc.shiro;

import com.google.gson.Gson;
import com.sxkj.common.util.code.MyResponseStatusEnum;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zwd
 * 前后端分离
 */
public class AuthFilter extends AuthenticatingFilter {
    /**
     * 生成自定义token
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 获取请求的token
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (token == null || "".equals(token)) {
            return null;
        }
        return new AuthToken(token);
    }

    /**
     * 步骤1.所有请求全部拒绝访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    /**
     * 步骤2，拒绝访问的请求，会调用onAccessDenied方法，onAccessDenied方法先获取 token，再调用executeLogin方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回
        String token = getRequestToken((HttpServletRequest) request);
        if ("".equals(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setCharacterEncoding("UTF-8");
            Map<String, Integer> result = new HashMap<>(2);
            result.put("code", MyResponseStatusEnum.TOKEN_ERROR.getCode());
            String json = new Gson().toJson(result);
            httpResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 登陆失败时候调用
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setCharacterEncoding("UTF-8");
        try {
            Map<String, Integer> result = new HashMap<>(2);
            result.put("code", MyResponseStatusEnum.FAIL.getCode());
            String json = new Gson().toJson(result);
            httpResponse.getWriter().print(json);
        } catch (Exception e1) {
            throw new AuthenticationException();
        }
        return false;
    }

    /**
     * 获取请求的token
     *
     * @param request
     * @return
     */
    private String getRequestToken(HttpServletRequest request) {
        System.err.println(request.getRequestURI());
        String token = request.getHeader("token");
        if (token == null || "".equals(token)) {
            token = request.getParameter("token");
        }
        return token == null ? "" : token;
    }
}
