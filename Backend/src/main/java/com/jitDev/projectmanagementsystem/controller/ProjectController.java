package com.jitDev.projectmanagementsystem.controller;

import com.jitDev.projectmanagementsystem.model.Chat;
import com.jitDev.projectmanagementsystem.model.Invitation;
import com.jitDev.projectmanagementsystem.model.Project;
import com.jitDev.projectmanagementsystem.model.User;
import com.jitDev.projectmanagementsystem.request.InviteRequest;
import com.jitDev.projectmanagementsystem.response.MessageResponse;
import com.jitDev.projectmanagementsystem.service.InvitationService;
import com.jitDev.projectmanagementsystem.service.ProjectService;
import com.jitDev.projectmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final InvitationService invitationService;

    public ProjectController(ProjectService projectService, UserService userService, InvitationService invitationService) {
        this.projectService = projectService;
        this.userService = userService;
        this.invitationService = invitationService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tags,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tags);

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);

        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);

        return ResponseEntity.ok(createdProject);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(project, projectId);

        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());

        MessageResponse res = new MessageResponse("project deleted successfully");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProject(keyword, user);

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);

        return ResponseEntity.ok(chat);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody InviteRequest req
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(), req.getProjectId());

        MessageResponse messageResponse = new MessageResponse("User Invited to the Project Successfully");
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInvite(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());
        return ResponseEntity.ok(invitation);
    }
}
