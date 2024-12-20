package com.backend.social_media.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    ResponseEntity<?> addPost(MultipartFile image);

    ResponseEntity<?> getAllPosts(int page, int size, String sortBy);

    ResponseEntity<?> updateLikes(String postId);
}
