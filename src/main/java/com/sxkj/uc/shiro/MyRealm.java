package com.sxkj.uc.shiro;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.LoginService;
import com.sxkj.uc.service.UserService;
import com.sxkj.uc.util.UcContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zwd
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    /**
     * 鉴权，检查用户权限或者角色时调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.error("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        log.error("doGetAuthorizationInfo 鉴权。。。。。。");
        HttpServletRequest request = new UcContext().getRequest();
        log.error(request.getRequestURI());
        log.error("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        if (principalCollection.getPrimaryPrincipal() == null) {
            return null;
        }
        String username = principalCollection.getPrimaryPrincipal().toString();
        User user = loginService.findUserByName(username);
        if(user == null ){
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String permit = userService.hasPermit(user.getUsername());
        info.addStringPermission(permit);
        /* 添加角色和权限
        info.addRole();
        info.addStringPermission();
        */
        return info;
    }

    /**
     * 认证,执行Subject的login方法是调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.error("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        log.error("doGetAuthenticationInfo 认证。。。。。。");
        HttpServletRequest request = new UcContext().getRequest();
        log.error(request.getRequestURI());
        log.error("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        String username = authenticationToken.getPrincipal().toString();
        User user = loginService.findUserByName(username);
        if (user == null) {
            return null;
        }else {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,user.getPassword(),getName());
            return info;
        }
    }
}
