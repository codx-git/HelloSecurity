package com.example.config.deprecated;

import com.example.filter.JwtAuthenticationFilter;
import com.example.filter.LogTraceIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//@Configuration
@Deprecated
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LogTraceIdFilter> logTraceIdFilterRegistration(){
        FilterRegistrationBean<LogTraceIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogTraceIdFilter());
        //定义过滤器路径
        registrationBean.addUrlPatterns("/*");
        //定义过滤器启动顺序，数值越小越先启动
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegistration(){
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter());
        //定义过滤器路径
        registrationBean.addUrlPatterns("/*");
        //定义过滤器启动顺序，数值越小越先启动
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
