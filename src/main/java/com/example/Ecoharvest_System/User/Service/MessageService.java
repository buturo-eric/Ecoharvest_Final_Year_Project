package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.MessageModel;
import com.example.Ecoharvest_System.User.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<MessageModel> findAllMessages() {
        return messageRepository.findAllByReplyIsNullOrderByDateTimeDesc(); // Fetch only top-level messages
    }

    public List<MessageModel> findReplies(MessageModel message) {
        return messageRepository.findAllByReplyOrderByDateTimeAsc(message);
    }

    public MessageModel saveMessage(MessageModel message) {
        return messageRepository.save(message);
    }

    public MessageModel findById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }
}
