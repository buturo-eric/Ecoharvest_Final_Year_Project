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
        return messageRepository.findAllByReplyIsNullAndIsVisibleTrueOrderByDateTimeDesc(); // Fetch only top-level visible messages
    }

    public List<MessageModel> findAllMessagesForAdmin() {
        return messageRepository.findAllByReplyIsNullOrderByDateTimeDesc(); // Fetch all top-level messages
    }

    public List<MessageModel> findReplies(MessageModel message) {
        return messageRepository.findAllByReplyAndIsVisibleTrueOrderByDateTimeAsc(message);
    }

    public List<MessageModel> findRepliesForAdmin(MessageModel message) {
        return messageRepository.findAllByReplyOrderByDateTimeAsc(message);
    }

    public MessageModel saveMessage(MessageModel message) {
        return messageRepository.save(message);
    }

    public MessageModel findById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public void updateMessageVisibility(Long id, boolean isVisible) {
        MessageModel message = findById(id);
        if (message != null) {
            message.setVisible(isVisible);
            messageRepository.save(message);
        }
    }
}

