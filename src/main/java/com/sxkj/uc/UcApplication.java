package com.sxkj.uc;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zwd
 */
//@EnableOAuth2Sso
@EnableSwagger2Doc
@SpringBootApplication
@ServletComponentScan
public class UcApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(UcApplication.class, args);
    }
//    TODO 使用security，消除shiro及interpreter的影响 ！！！
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor())
//                .addPathPatterns("/api/**")
//                .addPathPatterns("/verify");
//    }
//
//    @Bean
//    public AccessTokenInterceptor tokenInterceptor() {
//        return new AccessTokenInterceptor();
//    }
}
