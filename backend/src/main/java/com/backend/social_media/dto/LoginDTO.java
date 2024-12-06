package com.backend.social_media.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    @NotNull(message = "User Name is required.")
    private String userName;

    @NotNull(message = "Password is required.")
    private String password;
}
