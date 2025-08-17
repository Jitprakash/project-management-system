package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.model.Invitation;

public interface InvitationService {

    public void sendInvitation(String email,Long projectId) throws Exception;

    public Invitation acceptInvitation(String token,Long userId) throws Exception;

    public String getTokenByUserMail(String userMail) throws Exception;

    void deleteToken(String token);
}
