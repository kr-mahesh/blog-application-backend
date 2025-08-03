package com.blog.blogapp.repository;

import com.blog.blogapp.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}