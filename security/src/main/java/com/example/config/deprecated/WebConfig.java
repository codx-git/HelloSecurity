package com.example.config.deprecated;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@Configuration
@Deprecated
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new LogTraceIdInterceptor()).addPathPatterns("/**");
//    }
}
