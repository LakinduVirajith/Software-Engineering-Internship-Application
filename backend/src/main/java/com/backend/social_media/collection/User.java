package com.backend.social_media.collection;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Document(collection = "users")
public class User implements UserDetails {

    @NotNull(message = "Full Name is required.")
    @Size(min = 3, max = 50, message = "Full Name must be between 3 and 50 characters.")
    private String fullName;

    @NotNull(message = "User Name is required.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    @Getter
    private String userName;

    @Id
    @NotNull(message = "Email is required.")
    @Email(message = "Invalid email format. Please enter a valid email address.")
    private String email;

    @NotNull(message = "Password is required.")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters.")
    private String password;

    @NotNull(message = "Date of Birth is required.")
    @Past(message = "Date of Birth must be a past date.")
    private LocalDate dateOfBirth;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
