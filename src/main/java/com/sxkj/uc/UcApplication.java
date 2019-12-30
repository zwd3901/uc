package com.sxkj.uc;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.sxkj.uc.interceptor.AccessTokenInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zwd
 */
@EnableSwagger2Doc
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
})
@ServletComponentScan
public class UcApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(UcApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/api/**")
                .addPathPatterns("/verify");
    }

    @Bean
    public AccessTokenInterceptor tokenInterceptor() {
        return new AccessTokenInterceptor();
    }
}
