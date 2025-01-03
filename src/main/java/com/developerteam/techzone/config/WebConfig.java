package com.developerteam.techzone.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir; // application.properties'ten alınan yükleme yolu

    @PostConstruct
    public void init() {
        // Klasör yoksa oluştur
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Uploads klasörü başarıyla oluşturuldu: " + directory.getAbsolutePath());
            } else {
                System.err.println("Uploads klasörü oluşturulamadı.");
            }
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ana dizindeki 'uploads' klasörünü URL ile ilişkilendir
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:"+uploadDir);
    }

}
