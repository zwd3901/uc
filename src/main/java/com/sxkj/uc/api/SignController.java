package com.sxkj.uc.api;

import com.sxkj.common.params.ResponseEnum;
import com.sxkj.common.response.MyResponse;
import com.sxkj.common.response.MyResponseUtil;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.AccessTokenService;
import com.sxkj.uc.service.LoginService;
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
@Api(description = "登录、退出管理", tags = "sign")
@RestController
@Slf4j
public class SignController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MyExceptionHandler exceptionHandler;
    @Autowired
    private AccessTokenService tokenService;


    /**
     * 登录
     *
     * @param user
     * @returnq
     */
    @ApiOperation(value = "登录", notes = "传递用户名和密码")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true, dataType = "json")
    @GetMapping("/login")
    public MyResponse login(User user) {
        try {
            user = loginService.signIn(user.getUsername(), user.getPassword());
            String token = tokenService.createToken(user.getId());
            Map<String, Object> map = new HashMap<>(16);
            map.put("user", user);
            map.put("token", token);
            return MyResponseUtil.success(map);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 退出系统，清理token
     *
     * @param userId
     * @return
     */
    @GetMapping("/logout/{userId}")
    public MyResponse logout(@PathVariable String userId) {
        tokenService.deleteByUserId(userId);

        return MyResponseUtil.success(ResponseEnum.SUCCESS);
    }

    /**
     * slave端验证，通过shiro realm验证token
     *
     * @return
     */
//    @RequiresRoles("slave")
    @GetMapping("/verify")
    public MyResponse verify() {
        /**
         * TODO slave应用请求认证
         * TODO 目前只要token检查通过即返回true，还需做访问权限检查
         *
         */


        return MyResponseUtil.success();
    }
}
