package com.sxkj.uc.api;

import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SecurityVerifyController
 * @Description: TODO
 * @Author zwd
 * @Date 2019/12/20 0020
 **/
@RestController
@Slf4j
public class SecurityVerifyController {

    @GetMapping("/verify")
    public CustomResult verify() {
        return CustomResultUtil.success();
    }
}
