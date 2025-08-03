package com.blog.blogapp.service;

import com.blog.blogapp.dto.BlogRequest;
import com.blog.blogapp.dto.BlogResponse;
import com.blog.blogapp.model.Blog;
import com.blog.blogapp.model.User;
import com.blog.blogapp.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogResponse createBlog(BlogRequest request, User user) {
        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setAuthor(user);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());

        Blog saved = blogRepository.save(blog);
        return mapToDto(saved);
    }

//    public List<BlogResponse> getAllBlogs() {
//        return blogRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
//    }
        public Page<BlogResponse> getAllBlogs(Pageable pageable) {
            return blogRepository.findAll(pageable)
                    .map(this::mapToDto);
        }

    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Oops, Blog not found"));
        return mapToDto(blog);
    }

    public BlogResponse updateBlog(Long id, BlogRequest request, User user) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Oops, Blog not found"));

        if (!blog.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Oops, You are not the author of this blog");
        }
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setUpdatedAt(LocalDateTime.now());
        Blog updated = blogRepository.save(blog);
        return mapToDto(updated);
    }
    public void deleteBlog(Long id, User user) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Oops, Blog not found"));

        if (!blog.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Sorry, You are not the author of this blog");
        }

        blogRepository.delete(blog);
    }

    private BlogResponse mapToDto(Blog blog) {
        BlogResponse dto = new BlogResponse();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setContent(blog.getContent());
        dto.setAuthorEmail(blog.getAuthor().getEmail());
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setUpdatedAt(blog.getUpdatedAt());
        return dto;
    }
}