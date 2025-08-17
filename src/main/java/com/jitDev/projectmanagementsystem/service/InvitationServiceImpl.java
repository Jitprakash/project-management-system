package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.model.Invitation;
import org.springframework.stereotype.Service;

@Service
public class InvitationServiceImpl implements InvitationService{
    @Override
    public void sendInvitation(String email, Long projectId) {

    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) {
        return null;
    }

    @Override
    public String getTokenByUserMail(String userMail) {
        return "";
    }

    @Override
    public void deleteToken(String token) {

    }
}
