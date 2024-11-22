package com.example.vo;

import com.example.dto.BlogDTO;

import java.time.LocalDateTime;

public class BlogVO {
    private final transient BlogDTO blog;

    public BlogVO(BlogDTO blog) {
        this.blog = blog;
    }

    public String getBlogId() {
        return blog.getBlogId();
    }

    public String getBlogTitle() {
        return blog.getBlogTitle();
    }

    public String getUserId() {
        return blog.getUserId();
    }

    public String getContent() {
        return blog.getContent();
    }

    public LocalDateTime getCreatedAt() {
        return blog.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return blog.getUpdatedAt();
    }

    public Boolean getIsPublished() {
        return blog.getIsPublished();
    }

}
