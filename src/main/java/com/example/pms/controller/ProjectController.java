package com.example.pms.controller;

import com.example.pms.entity.Project;
import com.example.pms.entity.User;
import com.example.pms.service.AuthService;
import com.example.pms.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project,
                                                 @RequestHeader("Authorization") String auth) {
        User user = authService.getUserFromToken(auth);
        project.setUser(user);
        Project saved = projectService.createProject(project);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getUserProjects(@RequestHeader("Authorization") String auth) {
        User user = authService.getUserFromToken(auth);
        List<Project> projects = projectService.getProjectsByUser(user);
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        User user = authService.getUserFromToken(auth);
        boolean deleted = projectService.deleteUserProjectById(id, user);

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            System.out.println("Deletion failed: Project not found or unauthorized");
            return ResponseEntity.status(403).body("Not authorized or project not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestBody Project updatedProject,
            @RequestHeader("Authorization") String token) {
        User user = authService.getUserFromToken(token);
        Project project = projectService.updateProject(id, updatedProject, user);
        return ResponseEntity.ok(project);
    }
}
