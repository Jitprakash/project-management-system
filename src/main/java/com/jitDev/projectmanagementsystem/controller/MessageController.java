package com.jitDev.projectmanagementsystem.controller;

import com.jitDev.projectmanagementsystem.model.Chat;
import com.jitDev.projectmanagementsystem.model.Message;
import com.jitDev.projectmanagementsystem.model.User;
import com.jitDev.projectmanagementsystem.request.CreateMessageRequest;
import com.jitDev.projectmanagementsystem.service.MessageService;
import com.jitDev.projectmanagementsystem.service.ProjectService;
import com.jitDev.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, ProjectService projectService) {
        this.messageService = messageService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception {
        User user = userService.findUserById(request.getSenderId());
        Chat chat = projectService.getChatByProjectId(request.getProjectId());
        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessageByProjectId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessageByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
