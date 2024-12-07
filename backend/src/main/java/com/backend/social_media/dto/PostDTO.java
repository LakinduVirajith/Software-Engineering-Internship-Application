package com.backend.social_media.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDTO {
    @NotNull(message = "Please upload an image to create a post.")
    private MultipartFile image;

    @Size(min = 2, max = 500, message = "Content must be between 2 and 500 characters.")
    private String content;  
}
