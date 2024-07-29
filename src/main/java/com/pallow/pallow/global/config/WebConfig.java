package com.pallow.pallow.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**", "/js/**", "/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/js/", "classpath:/templates/")
                .setCacheControl(CacheControl.noCache());
    }
}