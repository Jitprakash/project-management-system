package com.jitDev.projectmanagementsystem.repository;

import com.jitDev.projectmanagementsystem.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation,Long> {

    Invitation findByToken(String token);

    Invitation findByEmail(String userEmail);
}
