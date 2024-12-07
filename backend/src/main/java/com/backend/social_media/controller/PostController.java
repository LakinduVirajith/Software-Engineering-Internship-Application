package com.backend.social_media.controller;

import com.backend.social_media.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> addPost(@RequestParam("file") MultipartFile image) {
        return postService.addPost(image);
    }
}
