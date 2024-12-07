package com.backend.social_media.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {
    
    private String userName;

    private String image;

    private Integer likes;

    private LocalDateTime modifiedDate;
}
