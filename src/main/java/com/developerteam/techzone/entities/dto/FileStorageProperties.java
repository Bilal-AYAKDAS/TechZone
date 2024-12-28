package com.developerteam.techzone.entities.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class FileStorageProperties {

    @Value("${file.upload-dir}")
    private String uploadDir;
}
