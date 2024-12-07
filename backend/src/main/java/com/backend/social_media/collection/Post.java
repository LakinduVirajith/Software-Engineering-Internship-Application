package com.backend.social_media.collection;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "posts")
public class Post {

    @Id
    private String _id;

    @NotNull(message = "User ID is required.")
    private String userId;

    private String image;

    private List<String> likes; // LIST OF USER IDs WHO LIKED THE POST

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Post() {
        this.likes = new ArrayList<>();
    }
}
