package com.jitDev.projectmanagementsystem.repository;

import com.jitDev.projectmanagementsystem.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
}
