package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.BlogDTO;
import com.example.entity.BlogDO;
import com.example.exception.BlogNotFoundException;
import com.example.mapper.BlogMapper;
import com.example.model.AddBlogRequest;
import com.example.model.UpdateBlogRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlogService extends ServiceImpl<BlogMapper, BlogDO> {

    @Transactional
    public BlogDO checkBlogExists(String blogId) {
        BlogDO blogDO = this.getById(blogId);
        if (blogDO == null) {
            throw new BlogNotFoundException("blog not found");
        }
        return blogDO;
    }

    // 获取所有已发布的博客文章
    public List<BlogDTO> getAllPublishedPosts() {
        return lambdaQuery()
                .eq(BlogDO::getIsPublished, true)  // 只查询已发布的文章
                .list()  // 返回的是 List<BlogDO>
                .stream()  // 使用 stream 进行转换
                .map(BlogDTO.newFromDO())  // 转换为 BlogDTO
                .collect(Collectors.toList());
    }

    // 创建新的博客文章，并且初始化为未发布状态
    @Transactional
    public BlogDTO createPost(AddBlogRequest addBlogRequest) {
        BlogDO blogDO = new BlogDO();
        blogDO.setBlogId(UUID.randomUUID().toString());
        blogDO.setBlogTitle(addBlogRequest.getBlogTitle());
        blogDO.setAuthorId(addBlogRequest.getUserId());
        blogDO.setContent(addBlogRequest.getContent());
        blogDO.setCreatedAt(LocalDateTime.now());
        blogDO.setUpdatedAt(LocalDateTime.now());
        blogDO.setIsPublished(false);  // 默认设置为未发布状态
        this.save(blogDO);  // 保存文章
        return BlogDTO.newFromDO().apply(blogDO);  // 转换为 DTO 并返回
    }

    // 更新博客文章
    @Transactional
    public BlogDTO updatePost(String blogId, UpdateBlogRequest updateBlogRequest) {
        BlogDO updatedBlog = this.getById(blogId);
        updatedBlog.setBlogTitle(updateBlogRequest.getBlogTitle());
        updatedBlog.setContent(updateBlogRequest.getContent());
        updatedBlog.setUpdatedAt(LocalDateTime.now());
        this.updateById(updatedBlog);  // 根据 ID 更新文章
        return BlogDTO.newFromDO().apply(updatedBlog);  // 转换为 DTO 并返回
    }

    // 发布博客文章
    @Transactional
    public boolean publishPost(String blogId) {
        BlogDO blogDO = checkBlogExists(blogId);
        blogDO.setIsPublished(true);  // 设置为已发布状态
        return this.updateById(blogDO);  // 更新数据库记录
    }

    // 取消发布博客文章
    @Transactional
    public boolean unpublishPost(String blogId) {
        BlogDO blogDO = checkBlogExists(blogId);
        blogDO.setIsPublished(false);  // 设置为未发布状态
        return this.updateById(blogDO);  // 更新数据库记录
    }

    // 根据博客 ID 删除博客
    @Transactional
    public boolean deletePost(String blogId) {
        checkBlogExists(blogId);
        return this.removeById(blogId);  // 根据 ID 删除博客文章
    }
}
