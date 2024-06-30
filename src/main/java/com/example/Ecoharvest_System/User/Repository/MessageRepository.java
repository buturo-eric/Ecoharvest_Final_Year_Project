package com.example.Ecoharvest_System.User.Repository;

import com.example.Ecoharvest_System.User.Model.MessageModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Long> {
    @EntityGraph(attributePaths = {"replies", "replies.user", "replies.replies"})
    List<MessageModel> findAllByReplyIsNullOrderByDateTimeDesc();

    List<MessageModel> findAllByReplyOrderByDateTimeAsc(MessageModel reply);
}

