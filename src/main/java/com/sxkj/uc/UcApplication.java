package com.sxkj.uc;

import com.sxkj.uc.auth.LoginInterceptor;
import com.sxkj.uc.auth.filter.RequestReplacedFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ServletComponentScan
public class UcApplication  implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(UcApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInterceptor());

        interceptorRegistration.addPathPatterns("/**").excludePathPatterns("/","/api/sign/login");
    }

    /**
     * 必须添加此方法，否则将无法获取其他注入
     * @return
     */
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
    /*@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new RequestReplacedFilter());
        bean.addUrlPatterns("/*");
        bean.addInitParameter("paramName","paramValue");
        bean.setName("httpServletRequestReplacedFilter");
        bean.setOrder(1);
        return bean;
    }*/

}
