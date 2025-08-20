package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.model.Chat;
import com.jitDev.projectmanagementsystem.model.Message;
import com.jitDev.projectmanagementsystem.model.User;
import com.jitDev.projectmanagementsystem.repository.MessageRepository;
import com.jitDev.projectmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, ProjectService projectService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.projectService = projectService;
    }

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User user = userRepository.findById(senderId).
                orElseThrow(()-> new Exception("User not found with id " + senderId));
        Chat chat = projectService.getChatByProjectId(projectId);

        Message message = new Message();
        message.setChat(chat);
        message.setSender(user);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        chat.getMessageList().add(savedMessage);

        return message;

    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);

        List<Message> messages = messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());

        return messages;
    }
}
