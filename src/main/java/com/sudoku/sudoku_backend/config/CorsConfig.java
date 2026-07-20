package com.sudoku.sudoku_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Cross-Origin Resource Sharing is a browser security mechanism that blocks your frontend port 5173 from talking to your backend port 8080 by default.
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Allow endpoints that start with /api/.
                .allowedOrigins("http://localhost:5173") // Allow requests from the frontend server.
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Allow all CRUD operations.
    }
}
