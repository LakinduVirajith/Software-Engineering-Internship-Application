package com.backend.social_media.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    
    @NotNull(message = "Full Name is required.")
    @Size(min = 3, max = 50, message = "Full Name must be between 3 and 50 characters.")
    private String fullName;

    @NotNull(message = "User Name is required.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    private String userName;

    @NotNull(message = "Email is required.")
    @Email(message = "Invalid email format. Please enter a valid email address.")
    private String email;

    @NotNull(message = "Password is required.")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters.")
    private String password;
}