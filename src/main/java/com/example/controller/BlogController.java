package com.example.controller;

import com.example.dto.BlogDTO;
import com.example.entity.BlogDO;
import com.example.model.AddBlogRequest;
import com.example.response.ApiResponse;
import com.example.service.BlogService;
import com.example.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/published", method = RequestMethod.GET)
    public List<BlogVO> getPublishedBlogs() {
        List<BlogDTO> blogDTOList = blogService.getAllPublishedPosts();
        List<BlogVO> blogVOList = new ArrayList<>();
        for (BlogDTO blogDTO : blogDTOList) {
            blogVOList.add(new BlogVO(blogDTO));
        }
        return blogVOList;
    }

    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public ApiResponse<String> publishBlog(@RequestBody AddBlogRequest addBlogRequest) {
        return null;
    }
}
