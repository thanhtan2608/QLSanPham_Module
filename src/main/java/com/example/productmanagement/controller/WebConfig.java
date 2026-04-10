package com.example.productmanagement.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ánh xạ link ảo /uploads/** vào thư mục vật lý uploads/ trên ổ cứng
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
