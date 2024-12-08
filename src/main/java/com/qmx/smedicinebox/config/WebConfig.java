package com.qmx.smedicinebox.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/pc/**") // 只拦截包含/pc的路径
                .excludePathPatterns("/pc/login")
                .excludePathPatterns("/pc/register")
        ;
    }



//    @Bean(name = "customSessionValidationFilter")
//    public FilterRegistrationBean<SessionValidationFilter> sessionValidationFilter() {
//        FilterRegistrationBean<SessionValidationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new SessionValidationFilter());
//        registrationBean.addUrlPatterns("/pc/*"); // Apply filter to protected URLs
//        return registrationBean;
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/videos/**")
//                .addResourceLocations("file:/C:/Users/xiaoz/xnetworkdisk/file/xnetworkdisk/");
//    }

}
