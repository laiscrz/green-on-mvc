package com.taligado.app.config;

import com.taligado.app.interceptor.UserEnterpriseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserEnterpriseInterceptor userEnterpriseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Aplica o interceptor em todos os controllers
        registry.addInterceptor(userEnterpriseInterceptor).addPathPatterns("/**");
    }
}
