package com.sxkj.uc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName SpringSecurityConfig
 * @Description: TODO
 * @Author zwd
 * @Date 2019/12/26 0026
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 未登陆时返回 JSON 格式的数据给前端（否则为 html）
     */
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    /**
     * 登录成功返回的 JSON 格式数据给前端（否则为 html）
     */
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    /**
     * 登录失败返回的 JSON 格式数据给前端（否则为 html）
     */
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    /**
     * 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）
     */
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    /**
     * 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
     */
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    /**
     * 自定义安全认证
     */
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login")
                .permitAll()

                .and()
                // 关闭csrf
                .csrf().disable()
                // 开启基本验证
                .httpBasic()
                // 未登录
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                // 登录成功
                .successHandler(myAuthenticationSuccessHandler)
                // 登录失败
                .failureHandler(myAuthenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                // 退出成功
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .permitAll()
                .and()
                // 无全访问
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
    }

}
