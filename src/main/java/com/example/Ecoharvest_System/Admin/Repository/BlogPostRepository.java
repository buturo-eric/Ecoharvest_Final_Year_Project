package com.example.Ecoharvest_System.Admin.Repository;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;


@Repository
public interface BlogPostRepository extends JpaRepository<BlogPostModel, Long> {
    long countByCreatedAt(LocalDateTime dateTime);
    long countByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    // Add this method to count posts from the previous day
    @Query("SELECT COUNT(b) FROM BlogPostModel b WHERE b.createdAt BETWEEN :start AND :end")
    long countPostsBetweenDates(LocalDateTime start, LocalDateTime end);
}
