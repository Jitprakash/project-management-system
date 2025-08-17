package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.model.Invitation;

public interface InvitationService {

    public void sendInvitation(String email,Long projectId);

    public Invitation acceptInvitation(String token,Long userId);

    public String getTokenByUserMail(String userMail);

    void deleteToken(String token);
}
