package com.backend.social_media.service;

import com.backend.social_media.collection.User;
import com.backend.social_media.config.jwt.JwtService;
import com.backend.social_media.dto.AuthenticationResponseDTO;
import com.backend.social_media.dto.LoginDTO;
import com.backend.social_media.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public ResponseEntity<?> userSignUp(User user) {
        try {
            if (userRepository.findUserByUserName(user.getUserName()).isPresent()) {
                return ResponseEntity.badRequest().body("The chosen username is unavailable. Please try a different one.");
            }
            if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("The provided email address is already associated with an existing account.");
            }

            // HASH THE PASSWORD BEFORE SAVING
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            userRepository.save(user);
            return ResponseEntity.status(201).body("User signed up successfully!");
        }catch (Exception e){
            log.error("Error occurred while signing up the user: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("An error occurred while signing up the user");
        }
    }

    @Override
    public ResponseEntity<?> userLogin(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUserName());

        // GENERATE ACCESS TOKEN AND REFRESH TOKEN
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok().body(AuthenticationResponseDTO.builder()
                .message("Welcome back! You've logged in successfully.")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build()
        );
    }
}
