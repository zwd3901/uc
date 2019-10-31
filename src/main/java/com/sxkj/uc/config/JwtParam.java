package com.sxkj.uc.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zwd
 * 获取自定义配置
 */
@Component
@Getter
public class JwtParam {
    @Value("${config.jwt.secret}")
    private String secret;
    @Value("${config.jwt.expire}")
    private long expire;
    @Value("${config.jwt.header}")
    private String header;
}
