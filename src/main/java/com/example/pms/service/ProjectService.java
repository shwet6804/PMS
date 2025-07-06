package com.example.pms.service;

import com.example.pms.entity.Project;
import com.example.pms.entity.User;
import com.example.pms.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Create a new project
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Get all projects of a user
    public List<Project> getProjectsByUser(User user) {
        return projectRepository.findByUser(user);
    }

    // Delete a project by ID if it belongs to the user
    public boolean deleteUserProjectById(Long id, User user) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent() && project.get().getUser().getId().equals(user.getId())) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Update project name and status if it belongs to the user
    public Project updateProject(Long id, Project updatedProject, User user) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isEmpty()) {
            System.out.println("❌ Project not found with id: " + id);
            throw new RuntimeException("Project not found");
        }

        Project existing = optionalProject.get();

        System.out.println("🔍 Existing project user ID: " + existing.getUser().getId());
        System.out.println("🔐 Logged-in user ID from token: " + user.getId());

        if (!existing.getUser().getId().equals(user.getId())) {
            System.out.println("🚫 Unauthorized update attempt by user " + user.getId() + " on project owned by " + existing.getUser().getId());
            throw new RuntimeException("Unauthorized update attempt");
        }

        if (updatedProject.getName() != null && !updatedProject.getName().isBlank()) {
            existing.setName(updatedProject.getName());
        }
        if (updatedProject.getStatus() != null && !updatedProject.getStatus().isBlank()) {
            existing.setStatus(updatedProject.getStatus());
        }

        System.out.println("✅ Updating project ID " + id + " with name = " + existing.getName() + " and status = " + existing.getStatus());

        return projectRepository.save(existing);
    }
    public Project getUserProjectById(Long id, User user) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty() || !project.get().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Project not found or unauthorized");
        }
        return project.get();
    }


}
