package com.sxkj.uc.shiro.conf;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zwd
 */
public class MyRealm extends AuthorizingRealm {
    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        /**
         * 根据userName获取用户权限信息
         */
        Set<String> set = new HashSet<>(16);
        set.add("user:show");
        set.add("user:admin");
        info.setStringPermissions(set);
        return info;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        /**
         * 1、 根据userName查找用户并获取登录密码
         * 2、 获取用户信息失败 throw exception
         * 3、 成功获取用户信息，则比对登录密码是否一致
         * 4、 密码不一致 throw exception
          */


        return new SimpleAuthenticationInfo(userName,password,getName());
    }
}
