package com.sxkj.uc.api;

import com.sxkj.common.util.MyResponse;
import com.sxkj.common.util.MyResponseUtil;
import com.sxkj.uc.entity.OnLine;
import com.sxkj.uc.service.AccessTokenService;
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
public class AccessTokenController {
    @Autowired
    private AccessTokenService tokenService;

    /**
     * 获取token
     *
     * @param userId
     * @return
     */
    @GetMapping("/check/{userId}")
    public MyResponse token(@PathVariable String userId) {
        OnLine sysToken = tokenService.findByUserId(userId);
        return MyResponseUtil.success(sysToken.getToken());
    }

    @GetMapping("/check")
    public MyResponse check(@RequestParam String token) {
        System.err.println(token);
        return MyResponseUtil.success(token);
    }
}
