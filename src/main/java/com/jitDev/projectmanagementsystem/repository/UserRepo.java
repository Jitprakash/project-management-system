package com.jitDev.projectmanagementsystem.repository;

import com.jitDev.projectmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    public User findByEmail(String Email);
}
