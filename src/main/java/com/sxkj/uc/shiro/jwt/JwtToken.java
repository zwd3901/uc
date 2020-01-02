package com.sxkj.uc.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * com.sxkj.util.jwt.JwtToken
 *
 * @author zwd
 * @Description JwtToken
 * @Date Create in 2018-07-12 0012 14:27
 * @Modified
 */
public class JwtToken extends UsernamePasswordToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
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
