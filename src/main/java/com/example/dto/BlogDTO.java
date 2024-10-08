package com.example.dto;

import com.example.entity.BlogDO;

import java.time.LocalDateTime;
import java.util.function.Function;

public class BlogDTO {
    private String blogId;

    private String blogTitle;

    private String userId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isPublished;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean published) {
        isPublished = published;
    }

    public static Function<BlogDO, BlogDTO> newFromDO() {
        return blogDO -> {
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setBlogId(blogDO.getBlogId());
            blogDTO.setBlogTitle(blogDO.getBlogTitle());
            blogDTO.setUserId(blogDO.getAuthorId());
            blogDTO.setContent(blogDO.getContent());
            blogDTO.setCreatedAt(blogDO.getCreatedAt());
            blogDTO.setUpdatedAt(blogDO.getUpdatedAt());
            blogDTO.setIsPublished(blogDO.getIsPublished());
            return blogDTO;
        };
    }

}
