package com.jitDev.projectmanagementsystem.repository;

import com.jitDev.projectmanagementsystem.model.Comment;
import com.jitDev.projectmanagementsystem.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIssue(Issue issue);
    //To Do: - showing error
}
