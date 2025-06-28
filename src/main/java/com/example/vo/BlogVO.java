package com.example.vo;

import com.example.dto.BlogDTO;

import java.time.LocalDateTime;

public class BlogVO {
    private final transient BlogDTO blogDTO;

    public BlogVO(BlogDTO blogDTO) {
        this.blogDTO = blogDTO;
    }

    public String getBlogId() {
        return blogDTO.getBlogId();
    }

    public String getBlogTitle() {
        return blogDTO.getBlogTitle();
    }

    public String getUserId() {
        return blogDTO.getUserId();
    }

    public String getContent() {
        return blogDTO.getContent();
    }

    public LocalDateTime getCreatedAt() {
        return blogDTO.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return blogDTO.getUpdatedAt();
    }

    public Boolean getIsPublished() {
        return blogDTO.getIsPublished();
    }

}
