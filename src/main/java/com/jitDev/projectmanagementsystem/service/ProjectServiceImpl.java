package com.jitDev.projectmanagementsystem.service;

import com.jitDev.projectmanagementsystem.model.Chat;
import com.jitDev.projectmanagementsystem.model.Project;
import com.jitDev.projectmanagementsystem.model.User;
import com.jitDev.projectmanagementsystem.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService, ChatService chatService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();

        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project savedProject = projectRepository.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {

        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        //Filter By Category
        if (category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).collect(Collectors.toList());

        }

        //Filter By Tags
        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag)).collect(Collectors.toList());

        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new Exception("project not found");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

        //get the project if it's not there it will throw a Exception
        //This is just to check if the project exists
        getProjectById(projectId);

        projectRepository.deleteById(projectId);

    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {

        Project project = getProjectById(id);

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());


        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (!project.getTeam().contains(user)){

            project.getChat().getUsers().add(user);//add the user so it can chat
            project.getTeam().add(user);//add the user to project
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (project.getTeam().contains(user)){

            project.getChat().getUsers().remove(user);//remove the user so it can chat
            project.getTeam().remove(user);//remove the user to project
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);

        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws Exception {
        return projectRepository.findByNameContainingAndTeamContains(keyword,user);
    }
}
