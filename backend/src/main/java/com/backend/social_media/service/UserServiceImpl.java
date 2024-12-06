package com.backend.social_media.service;

import com.backend.social_media.collection.User;
import com.backend.social_media.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<?> userSignUp(User user) {
        try {
            if (userRepository.findUserByEmailAddress(user.getEmailAddress()).isPresent()) {
                return ResponseEntity.badRequest().body("Email address is already in use.");
            }

            // Hash the password before saving
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            userRepository.save(user);
            return ResponseEntity.status(201).body("User signed up successfully!");
        }catch (Exception e){
            log.error("Error occurred while signing up the user: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("An error occurred while signing up the user");
        }
    }
}
