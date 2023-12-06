package com.itzc.rear_end.config;

import com.itzc.rear_end.config.interceptor.Jwtlnterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")//拦截所有请求 ，判断token是否合法 决定是否需要登录
                .excludePathPatterns("policycomment/**","/comment/**/" ,"/homepic/findByEnable","/policy/findAll","/policy/findById/*","/policy/page","/sys-user/login","/sys-user/sgin","/sys-user/import","/sys-user/export","/garbage/**","/file/**","/echarts/**","/daily-time/**","/knowledge/**","/article/**");
    }

    @Bean
    public Jwtlnterceptor jwtInterceptor(){
        return new Jwtlnterceptor();
    }
}
