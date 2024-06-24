package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserBlogsService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    public List<BlogPostModel> findAllPosts() {
        return blogPostRepository.findAll();
    }

}
