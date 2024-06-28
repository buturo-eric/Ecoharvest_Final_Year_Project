package com.example.Ecoharvest_System.User.Repository;

import com.example.Ecoharvest_System.User.Model.BlogViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogViewRepository extends JpaRepository<BlogViewModel, Long> {
    List<BlogViewModel> findByUserId(Long userId);

    List<BlogViewModel> findByBlogId(Long blogId);

    boolean existsByUserIdAndBlogId(int userId, Long blogId);
}
