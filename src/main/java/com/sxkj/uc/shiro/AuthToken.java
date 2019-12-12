package com.sxkj.uc.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author zwd
 * 自定义AuthenticationToken
 */
public class AuthToken extends UsernamePasswordToken implements AuthenticationToken {
    private String token;

    public AuthToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
