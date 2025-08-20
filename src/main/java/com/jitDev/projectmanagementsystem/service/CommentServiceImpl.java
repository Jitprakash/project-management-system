package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.model.Comment;
import com.jitDev.projectmanagementsystem.model.Issue;
import com.jitDev.projectmanagementsystem.model.User;
import com.jitDev.projectmanagementsystem.repository.CommentRepository;
import com.jitDev.projectmanagementsystem.repository.IssueRepository;
import com.jitDev.projectmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, IssueRepository issueRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment createComment(Long issueId, Long userId, String comment) throws Exception {

        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (issueOptional.isEmpty()) {
            throw new Exception("Issue not found with id " + issueId);
        }

        if (userOptional.isEmpty()) {
            throw new Exception("User not found with id " + userId);
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment newComment = new Comment();

        newComment.setIssues(issue);
        newComment.setUser(user);
        newComment.setCreatedDateTime(LocalDateTime.now());
        newComment.setContent(comment);

        Comment savedComment = commentRepository.save(newComment);

        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (commentOptional.isEmpty()) {
            throw new Exception("Comment not found with id " + commentId);
        }

        if (userOptional.isEmpty()) {
            throw new Exception("User not found with id " + userId);
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if (comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }else {
            throw new Exception("User doesn't have permission to delete the comment");
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
