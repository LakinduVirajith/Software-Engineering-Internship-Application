package com.backend.social_media.collection;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String _id;

    private String fullName;

    private String userName;

    private String email;

    private String password;

    private LocalDate dateOfBirth;

    @CreatedDate
    private LocalDateTime createdDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getUserName() {
        return userName;
    }
}
