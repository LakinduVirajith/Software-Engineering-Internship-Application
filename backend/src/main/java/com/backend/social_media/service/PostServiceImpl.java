package com.backend.social_media.service;

import com.backend.social_media.collection.Post;
import com.backend.social_media.common.CommonService;
import com.backend.social_media.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommonService commonService;

    @Override
    public ResponseEntity<?> addPost(MultipartFile image) {
        try {
            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body("Please upload an image to create a post.");
            }

            long maxSizeInBytes = 2 * 1024 * 1024; // 2MB
            if (image.getSize() > maxSizeInBytes) {
                return ResponseEntity.badRequest().body("Image size exceeds the maximum limit of 2MB.");
            }

            // CONVERT THE IMAGE TO BASE64
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
            Post post = new Post();
            post.setUserId(commonService.getUserId());
            post.setImage(base64Image);

            postRepository.save(post);
            return ResponseEntity.ok("Post created successfully with the uploaded image.");
        }catch (Exception e){
            log.error("Error occurred while saving the post: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("An error occurred while creating the post.");
        }
    }
}
