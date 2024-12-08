package com.backend.social_media.controller;

import com.backend.social_media.dto.LoginDTO;
import com.backend.social_media.dto.UserDTO;
import com.backend.social_media.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> userSignUp(@Valid @RequestBody UserDTO userDTO) {
        return userService.userSignUp(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.userLogin(loginDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> userProfile() {
        return userService.userProfile();
    }
}
