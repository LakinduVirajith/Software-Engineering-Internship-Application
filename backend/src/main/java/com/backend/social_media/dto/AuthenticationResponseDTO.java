package com.backend.social_media.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    @NotNull
    private String message;

    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;
}
