package com.sxkj.uc;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.jwt.JwtConfig;
import com.sxkj.uc.service.LoginService;
import com.sxkj.uc.service.UserService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultCodeEnum;
import com.sxkj.uc.util.CustomResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zwd
 * 注册、登录、退出
 */
@RestController
@RequestMapping("/api/sign")
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
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/add")
    public CustomResult signUp(@RequestBody User user){
        String id = userService.createUser(user);
        user.setId(id);

        return CustomResultUtil.success(user);
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @GetMapping("/login")
    public CustomResult signIn(@RequestBody User user) {
        user = loginService.signIn(user.getLoginName(), user.getLoginPassword());
        if (user == null) {
            return CustomResultUtil.info(CustomResultCodeEnum.LOG_IN_FAIL);
        }else {
            Map<String,String> map = new HashMap(2);
            map.put(header,jwtConfig.createToken(user.getId()));
            return CustomResultUtil.success(map);
        }
    }

}
