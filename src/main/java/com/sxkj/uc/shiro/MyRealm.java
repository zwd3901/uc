package com.sxkj.uc.shiro;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.AccessTokenService;
import com.sxkj.uc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zwd
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private AccessTokenService tokenService;

    /**
     * 授权，验证用户权限或者角色时调用
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.error("doGetAuthorizationInfo 鉴权 。。。。。。。。。。。");
        if (principalCollection.getPrimaryPrincipal() == null) {
            return null;
        }
        // 获取登录用户信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        if (user == null) {
            return null;
        }
        // 添加权限(角色)
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String permit = userService.hasPermit(user.getUsername());
        info.addStringPermission(permit);
        info.addRole("slaves");
        /* 添加角色和权限
        info.addRole();
        info.addStringPermission();
        */
        return info;
    }

    /**
     * 认证 登录时调用
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.error("doGetAuthenticationInfo 认证 。。。。。。。。。。。");
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 1. 获取token
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        String token = (String) authenticationToken.getPrincipal();
        // 2. 获取登录用户信息
        User user = tokenService.findUserByToken(token);
        if (user == null) {
            throw new IncorrectCredentialsException("用户不存在");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, getName());
        return info;
    }
}
