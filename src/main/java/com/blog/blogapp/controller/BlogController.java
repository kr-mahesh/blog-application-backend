package com.blog.blogapp.controller;

import com.blog.blogapp.dto.BlogRequest;
import com.blog.blogapp.dto.BlogResponse;
import com.blog.blogapp.model.User;
import com.blog.blogapp.repository.UserRepository;
//import com.blog.blogapp.security.CustomUserDetails;
import com.blog.blogapp.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(
            @RequestBody BlogRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(blogService.createBlog(request, user));
    }
//    @GetMapping
//    public ResponseEntity<List<BlogResponse>> getAllBlogs() {
//        return ResponseEntity.ok(blogService.getAllBlogs());
//    }
    @GetMapping
    public ResponseEntity<Page<BlogResponse>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "61") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(blogService.getAllBlogs(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlog(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long id,
            @RequestBody BlogRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(blogService.updateBlog(id, request, user));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        blogService.deleteBlog(id, user);
        return ResponseEntity.ok("Blog deleted successfully");
    }
}