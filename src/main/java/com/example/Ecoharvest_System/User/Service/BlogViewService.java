package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.BlogViewModel;
import com.example.Ecoharvest_System.User.Repository.BlogViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogViewService {

    @Autowired
    private BlogViewRepository blogViewRepository;

    public BlogViewModel saveBlogView(BlogViewModel blogView) {
        return blogViewRepository.save(blogView);
    }

    public List<BlogViewModel> getAllBlogViews() {
        return blogViewRepository.findAll();
    }

    public List<BlogViewModel> getBlogViewsByBlogId(Long blogId) {
        return blogViewRepository.findByBlogId(blogId);
    }

    public boolean isBlogViewedByUser(int userId, Long blogId) {
        return blogViewRepository.existsByUserIdAndBlogId(userId, blogId);
    }

    public long countViewsByBlogId(Long blogId) {
        return blogViewRepository.countByBlogId(blogId);
    }
}
