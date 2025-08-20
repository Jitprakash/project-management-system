package com.jitDev.projectmanagementsystem.repository;

import com.jitDev.projectmanagementsystem.model.Comment;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@ReadingConverter
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIssueId(Long issueId);
}
