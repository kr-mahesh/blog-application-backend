package com.blog.blogapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogResponse {
    private Long id;
    private String title;
    private String content;
    private String authorEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}