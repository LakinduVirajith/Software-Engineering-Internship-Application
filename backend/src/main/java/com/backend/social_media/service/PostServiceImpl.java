package com.backend.social_media.service;

import com.backend.social_media.collection.Post;
import com.backend.social_media.common.CommonService;
import com.backend.social_media.dto.PostDTO;
import com.backend.social_media.repository.PostRepository;
import com.backend.social_media.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
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

    @Override
    public ResponseEntity<?> getAllPosts(int page, int size, String sortBy) {
        try {
            Pageable pageable;
            if (sortBy.equals("time")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
            } else if (sortBy.equals("likes")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("likeCount")));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
            }

            Page<Post> posts = postRepository.findAll(pageable);
            if (posts.isEmpty()) {
                return ResponseEntity.status(404)
                                    .body("No more posts available at the moment. Please check back later.");
            }        
        
            List<PostDTO> postDTOs = posts.stream()
                    .map(this::convertToPostDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(postDTOs);
        }catch (Exception e){
            log.error("Error occurred while retrieving the post: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("An error occurred while retrieving the post.");
        }
    }

    private PostDTO convertToPostDTO(Post post) {
        return PostDTO.builder()
                        .postId(post.get_id())
                        .userName(userRepository.findById(post.getUserId()).get().getUserName())
                        .image(post.getImage())
                        .likes(post.getLikeCount())
                        .isLiked(post.getLikes().contains(commonService.getUserId()))
                        .modifiedDate(post.getModifiedDate())
                        .build();
    }

    @Override
    public ResponseEntity<?> updateLikes(String postId) {
        try{
            Optional<Post> postOptional = postRepository.findById(postId);
            if (postOptional.isEmpty()) {
                return ResponseEntity.status(404)
                                    .body("Post not found.");
            }

            Post post = postOptional.get();
            List<String> likes = post.getLikes();

            if (likes.contains(commonService.getUserId())) {
                likes.remove(commonService.getUserId()); // UNLIKE THE POST
            } else {
                likes.add(commonService.getUserId()); // LIKE THE POST
            }

            post.setLikeCount(post.getLikes().size());
            post.setLikes(likes);
            postRepository.save(post);

            return ResponseEntity.ok("Post likes updated successfully.");
        }catch (Exception e){
            log.error("Error occurred while updating the likes: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("An error occurred while updating the likes.");
        }
    }
}
