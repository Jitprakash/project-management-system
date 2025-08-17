package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.model.Invitation;
import com.jitDev.projectmanagementsystem.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {


    private final InvitationRepository invitationRepository;
    private final EmailService emailService;

    public InvitationServiceImpl(InvitationRepository invitationRepository, EmailService emailService) {
        this.invitationRepository = invitationRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendInvitation(String email, Long projectId) throws Exception {
        //Create Invitation Token
        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink = "http://localhost:5173/accept_invitation?token=" + invitationToken;

        emailService.sendEmailWithToken(email, invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if (invitation == null) {
            throw new Exception("Invalid Invitation Token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userMail) throws Exception {

        Invitation invitation = invitationRepository.findByEmail(userMail);
        if (invitation == null) {
            throw new Exception("Invalid Invitation Token");
        }

        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);
    }
}
