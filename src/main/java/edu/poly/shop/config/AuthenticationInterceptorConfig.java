package edu.poly.shop.config;

import edu.poly.shop.interceptor.AdminAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationInterceptorConfig implements WebMvcConfigurer{
    @Autowired
    private AdminAuthenticationInterceptor adminAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthenticationInterceptor)
                .addPathPatterns("/admin/**")    //Config, if access to /admin/** without login, redirect to login screen.
                .excludePathPatterns("/admin/accounts/add");
    }
}
