package com.sxkj.uc.api;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.LoginService;
import com.sxkj.uc.service.TokenService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import com.sxkj.uc.util.MyExceptionHandler;
import com.sxkj.uc.util.code.CustomResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zwd
 * 登录、退出
 */
@Api(description = "登录、退出管理",tags = "sign")
@RestController
@Slf4j
public class SignController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MyExceptionHandler exceptionHandler;
    @Autowired
    private TokenService tokenService;



    /**
     * 登录
     * @param user
     * @returnq
     */
    @ApiOperation(value = "登录",notes = "传递用户名和密码")
    @ApiImplicitParam(name = "user",value = "用户对象",required = true,dataType = "json")
    @GetMapping("/login")
    public CustomResult login(User user) {
        try {
            user = loginService.signIn(user.getUsername(), user.getPassword());
            if (user == null) {
                return CustomResultUtil.info(CustomResultCodeEnum.LOG_IN_FAIL);
            }else {
                String token = tokenService.createToken(user.getId());
                Map<String, Object> map = new HashMap<>(16);
                map.put("user", user);
                map.put("token",token);
                return CustomResultUtil.success(map);
            }
        }catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 退出系统，清理token
     * @param userId
     * @return
     */
    @GetMapping("/logout/{userId}")
    public CustomResult logout(@PathVariable String userId) {
        tokenService.deleteByUserId(userId);

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
