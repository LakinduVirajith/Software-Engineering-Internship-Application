package com.backend.social_media.service;

import com.backend.social_media.collection.User;
import com.backend.social_media.dto.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> userSignUp(@Valid User user);

    ResponseEntity<?> userLogin(@Valid LoginDTO loginDTO);
}
