package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.model.User;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long id) throws Exception;

    User updateUsersProjectSize(User user, int size) throws Exception;
}
