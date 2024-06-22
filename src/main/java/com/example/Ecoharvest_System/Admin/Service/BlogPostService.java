package com.example.Ecoharvest_System.Admin.Service;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    public List<BlogPostModel> findAll() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPostModel> findById(Long id) {
        return blogPostRepository.findById(id);
    }

    public BlogPostModel save(BlogPostModel blogPost) {
        return blogPostRepository.save(blogPost);
    }

    public void deleteById(Long id) {
        blogPostRepository.deleteById(id);
    }

    public long countTodayPosts() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(); // Start of the day
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX); // End of the day

        return blogPostRepository.countByCreatedAtBetween(startOfDay, endOfDay);
    }

    public long countPreviousDayPosts() {
        LocalDateTime startOfPreviousDay = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endOfPreviousDay = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
        return blogPostRepository.countByCreatedAtBetween(startOfPreviousDay, endOfPreviousDay);
    }

    public double calculatePostPercentageChange(long currentCount, long previousCount) {
        if (previousCount == 0) {
            return currentCount > 0 ? 100.0 : 0.0; // New entries or no change
        }
        return ((double) (currentCount - previousCount) / previousCount) * 100;
    }

}

