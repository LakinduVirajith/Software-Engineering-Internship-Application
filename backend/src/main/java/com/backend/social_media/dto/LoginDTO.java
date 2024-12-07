package com.backend.social_media.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {

    @NotNull(message = "User Name is required.")
    private String userName;

    @NotNull(message = "Password is required.")
    private String password;
}
