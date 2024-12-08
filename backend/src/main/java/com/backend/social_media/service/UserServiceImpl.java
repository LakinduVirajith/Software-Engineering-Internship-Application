package com.backend.social_media.service;

import com.backend.social_media.collection.User;
import com.backend.social_media.common.CommonService;
import com.backend.social_media.config.jwt.JwtService;
import com.backend.social_media.dto.AuthenticationResponseDTO;
import com.backend.social_media.dto.LoginDTO;
import com.backend.social_media.dto.UserDTO;
import com.backend.social_media.dto.UserProfileDTO;
import com.backend.social_media.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Optional;

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
    private final CommonService commonService;

    @Override
    public ResponseEntity<?> userSignUp(UserDTO userDTO) {
        try {
            if (userRepository.findUserByUserName(userDTO.getUserName()).isPresent()) {
                return ResponseEntity.badRequest().body("The chosen username is unavailable. Please try a different one.");
            }
            if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("The provided email address is already associated with an existing account.");
            }

            // HASH THE PASSWORD BEFORE SAVING
            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
            User user = User.builder()
                            .fullName(userDTO.getFullName())
                            .userName(userDTO.getUserName())
                            .email(userDTO.getEmail())
                            .password(hashedPassword)
                            .build();

            if (userDTO.getProfileImage() != null && userDTO.getProfileImage().isEmpty()) {
                long maxSizeInBytes = 2 * 1024 * 1024; // 2MB
                if (userDTO.getProfileImage().getSize() > maxSizeInBytes) {
                    return ResponseEntity.badRequest().body("Image size exceeds the maximum limit of 2MB.");
                }

                // CONVERT THE IMAGE TO BASE64
                String base64Image = Base64.getEncoder().encodeToString(userDTO.getProfileImage().getBytes());
                user.setProfileImage(base64Image);
            }
                            
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

    @Override
    public ResponseEntity<?> userProfile() {
        Optional<User> optionalUser = userRepository.findById(commonService.getUserId());

        if(optionalUser.isEmpty()){
            return ResponseEntity.status(404)
                                    .body("Post not found.");
        }

        User user = optionalUser.get();
        UserProfileDTO userProfileDTO = UserProfileDTO.builder()
                        .fullName(user.getFullName())
                        .userName(user.getUserName())
                        .profileImage(user.getProfileImage())
                        .build();
        return ResponseEntity.ok(userProfileDTO);
    }
}
