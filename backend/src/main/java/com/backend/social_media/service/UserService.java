package com.backend.social_media.service;

import com.backend.social_media.dto.LoginDTO;
import com.backend.social_media.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> userSignUp(@Valid UserDTO userDTO);

    ResponseEntity<?> userLogin(@Valid LoginDTO loginDTO);

    ResponseEntity<?> userProfile();
}
