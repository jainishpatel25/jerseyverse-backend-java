package com.ecommerce.jerseyverse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String productUploadDir;

    public WebConfig(
            @Value("${app.upload.product-dir}") String productUploadDir) {
        this.productUploadDir = productUploadDir;
    }

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        Path uploadPath = Paths.get(productUploadDir)
                .toAbsolutePath()
                .normalize();

        String resourceLocation =
                uploadPath.toUri().toString();

        registry
                .addResourceHandler("/uploads/products/**")
                .addResourceLocations(resourceLocation);
    }
}