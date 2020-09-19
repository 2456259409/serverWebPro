package com.renjian.config;

import com.renjian.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/api/user/login","/api/user/register");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //项目中的所有接口都支持跨域
//        registry.addMapping("/**")
//                //所有地址都可以访问，也可以配置具体地址
//                .allowedOrigins("*")
//                //允许的请求方式
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                //是否支持跨域Cookie
//                .allowCredentials(true)
//                // 跨域允许时间
//                .maxAge(3600);
//    }
}
