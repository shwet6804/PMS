package com.example.pms.service;

import com.example.pms.entity.Project;
import com.example.pms.entity.Task;
import com.example.pms.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByProject(Project project) {
        return taskRepository.findByProject(project);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long taskId, boolean completed) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(completed);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public List<Task> filterByPriority(Project project, String priority) {
        return taskRepository.findByProjectAndPriority(project, priority);
    }

    public List<Task> filterByCompletion(Project project, boolean completed) {
        return taskRepository.findByProjectAndCompleted(project, completed);
    }
}
