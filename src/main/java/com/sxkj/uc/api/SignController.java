package com.sxkj.uc.api;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.LoginService;
import com.sxkj.uc.service.SysTokenService;
import com.sxkj.uc.service.UserService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import com.sxkj.uc.util.MyExceptionHandler;
import com.sxkj.uc.util.code.CustomResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zwd
 * 登录、退出
 */
@RestController
@Slf4j
public class SignController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MyExceptionHandler exceptionHandler;
    @Autowired
    private SysTokenService sysTokenService;



    /**
     * 登录
     * @param user
     * @return
     */
    @GetMapping("/login")
    public CustomResult login(User user) {
        try {
            user = loginService.signIn(user.getUsername(), user.getPassword());
            if (user == null) {
                return CustomResultUtil.info(CustomResultCodeEnum.LOG_IN_FAIL);
            }else {
                String token = sysTokenService.createToken(user.getId());
                Map<String, Object> map = new HashMap<>(16);
                map.put("user", user);
                map.put("token",token);
                return CustomResultUtil.success(map);
            }
        }catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    @GetMapping("/logout")
    public CustomResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return CustomResultUtil.success(CustomResultCodeEnum.LOG_OUT_SUCCESS);

    }

    @GetMapping("/index")
    public CustomResult index(){
        return CustomResultUtil.success();
    }

    @GetMapping("/error")
    public CustomResult error(){
        return CustomResultUtil.success("error");
    }

}
