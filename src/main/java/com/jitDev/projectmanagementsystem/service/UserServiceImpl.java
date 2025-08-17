package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.config.JwtProvider;
import com.jitDev.projectmanagementsystem.model.User;
import com.jitDev.projectmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("user not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        return user.get();
    }

    @Override
    public User updateUsersProjectSize(User user, int size) throws Exception {
        user.setProjectSize(user.getProjectSize() + size);

        return userRepository.save(user);
    }
}
