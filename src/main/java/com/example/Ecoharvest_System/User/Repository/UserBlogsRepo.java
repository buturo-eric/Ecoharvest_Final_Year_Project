package com.example.Ecoharvest_System.User.Repository;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlogsRepo extends JpaRepository<BlogPostModel, Long> {

}
