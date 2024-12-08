package com.backend.social_media.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDTO {

    private String fullName;
    
    private String userName;

    private String profileImage;
}
