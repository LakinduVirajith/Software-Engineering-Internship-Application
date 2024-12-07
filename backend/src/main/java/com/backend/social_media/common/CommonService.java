package com.backend.social_media.common;

import com.backend.social_media.collection.User;
import com.backend.social_media.config.jwt.JwtService;
import com.backend.social_media.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonService {

    private static String token;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public void storeJWT(String jwt) {
        token = jwt;
    }

    public String getUserEmail(){
        return jwtService.extractUsername(token);
    }

    public String getUserId(){
        return getUser().get_id();
    }

    public User getUser() {
        Optional<User> user = userRepository.findUserByEmail(getUserEmail());
        return user.get();
    }
}
