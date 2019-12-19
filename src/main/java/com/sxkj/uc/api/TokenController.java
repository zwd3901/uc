package com.sxkj.uc.api;

import com.sxkj.uc.entity.Token;
import com.sxkj.uc.service.TokenService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TokenController
 * @Description: TODO
 * @Author zwd
 * @Date 2019/12/12 0012
 **/
@Api(description = "token管理")
@Slf4j
@RestController
@RequestMapping("/api/token")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    /**
     * 获取token
     *
     * @param userId
     * @return
     */
    @GetMapping("/check/{userId}")
    public CustomResult token(@PathVariable String userId) {
        Token sysToken = tokenService.findByUserId(userId);
        return CustomResultUtil.success(sysToken.getToken());
    }

    @GetMapping("/check")
    public CustomResult check(@RequestParam String token) {
        System.err.println(token);
        return CustomResultUtil.success(token);
    }
}
