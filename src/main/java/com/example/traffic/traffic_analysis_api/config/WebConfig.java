package com.example.traffic.traffic_analysis_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // toutes les routes
                .allowedOrigins("*") // autoriser toutes les origines (à adapter en prod)
                .allowedMethods("*") // GET, POST, PUT, DELETE, etc.
                .allowedHeaders("*");
    }
}
