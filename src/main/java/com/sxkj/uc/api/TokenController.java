package com.sxkj.uc.api;

import com.sxkj.uc.entity.Token;
import com.sxkj.uc.service.TokenService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TokenController
 * @Description: TODO
 * @Author zwd
 * @Date 2019/12/12 0012
 **/
@Slf4j
@RestController
@RequestMapping("/api/token")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    /**
     * 获取token
     * @param userId
     * @return
     */
    @GetMapping("/token/{userId}")
    public CustomResult token(@PathVariable String userId){
        Token sysToken = tokenService.findByUserId(userId);
        return CustomResultUtil.success(sysToken.getToken());
    }
}
