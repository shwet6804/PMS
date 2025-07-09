package com.example.pms.controller;

import com.example.pms.entity.Project;
import com.example.pms.entity.Task;
import com.example.pms.entity.User;
import com.example.pms.service.AuthService;
import com.example.pms.service.ProjectService;
import com.example.pms.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthService authService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long projectId,
                                               @RequestHeader("Authorization") String token,
                                               @RequestParam(required = false) String priority,
                                               @RequestParam(required = false) Boolean completed) {
        User user = authService.getUserFromToken(token);
        Project project = projectService.getUserProjectById(projectId, user);

        if (priority != null) {
            return ResponseEntity.ok(taskService.filterByPriority(project, priority));
        } else if (completed != null) {
            return ResponseEntity.ok(taskService.filterByCompletion(project, completed));
        } else {
            return ResponseEntity.ok(taskService.getTasksByProject(project));
        }
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Task> addTask(@PathVariable Long projectId,
                                        @RequestBody Task task,
                                        @RequestHeader("Authorization") String token) {
        User user = authService.getUserFromToken(token);
        Project project = projectService.getUserProjectById(projectId, user);
        task.setProject(project);
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long taskId,
                                                 @RequestBody boolean completed) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, completed));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }
}
