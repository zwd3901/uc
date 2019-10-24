package com.sxkj.uc.api;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.jwt.JwtConfig;
import com.sxkj.uc.service.LoginService;
import com.sxkj.uc.service.UserService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.code.CustomResultCodeEnum;
import com.sxkj.uc.util.CustomResultUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zwd
 * 登录、退出
 */
@RestController
@RequestMapping("/api/sign")
@Slf4j
public class SignController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtConfig jwtConfig;

    @Value("${config.jwt.header}")
    private String header;

    /**
     * 登录
     * @param user
     * @return
     */
    @GetMapping("/login")
    public CustomResult signIn(@RequestBody User user) {
        /**
         * 1、在t_user表中根据登录名查找
         * 2、如果找到相关记录，比较登录密码
         * 2.1、 密码相同，转到步骤 // todo
         * 2.2、 密码不同，禁止登陆，返回相应消息
         * 3、如果t_user中没有，在t_user_app中查找
         * 4、t_user_app有，比较登录密码
         * 4.1、 密码相同，转到步骤 // todo
         * 4.2、 密码不同，禁止登录，返回相应消息
         * 5、 允许登录，查找可访问的应用列表，对比是否允许访问
         * 5.1、 允许访问，跳转
         * 5.2、 禁止访问，返回相应信息
         */
        user = loginService.signIn(user.getLoginName(), user.getLoginPassword());
        if (user == null) {
            return CustomResultUtil.info(CustomResultCodeEnum.LOG_IN_FAIL);
        }else {
            Map<String,Object> map = new HashMap(2);
            String token = jwtConfig.createToken(user.getId());
            log.info(user.getLoginName()+"==>>"+token);
            Claims claims = jwtConfig.getTokenClaims(token);
            log.info(claims.getSubject());
            map.put(header,token);
            map.put("user", user);
            return CustomResultUtil.success(map);
        }
    }

}
