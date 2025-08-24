package com.jitDev.projectmanagementsystem.controller;

import com.jitDev.projectmanagementsystem.model.Comment;
import com.jitDev.projectmanagementsystem.model.User;
import com.jitDev.projectmanagementsystem.request.CreateCommentRequest;
import com.jitDev.projectmanagementsystem.response.MessageResponse;
import com.jitDev.projectmanagementsystem.service.CommentService;
import com.jitDev.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(
                req.getIssueId(),
                user.getId(),
                req.getContent()
        );

        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user =userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessageResponse("Comment Deleted Successfully");

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(@PathVariable Long issueId) throws Exception {
        List<Comment> comments = commentService.findCommentByIssueId(issueId);

        return ResponseEntity.ok(comments);
    }
}
