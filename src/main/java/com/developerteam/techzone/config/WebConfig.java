package com.developerteam.techzone.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ana dizindeki 'uploads' klasörünü URL ile ilişkilendir
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

}
