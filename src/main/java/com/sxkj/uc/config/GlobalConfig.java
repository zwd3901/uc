package com.sxkj.uc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName GlobalConfig
 * @Description: TODO 全局配置
 * @Author zwd
 * @Date 2019/12/31 0031
 **/
@Configuration
public class GlobalConfig implements WebMvcConfigurer {
    /**
     * 跨域请求配置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 允许跨域的路径
                .addMapping("/**")
                // 允许跨域请求的域名
                .allowedOrigins("*")
                // 允许证书
                .allowCredentials(true)
                // 允许的方法
                .allowedMethods("*")
                // 允许跨域的时间
                .maxAge(3600);
    }
}
