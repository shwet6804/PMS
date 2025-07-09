package com.example.pms.repository;

import com.example.pms.entity.Project;
import com.example.pms.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);

    List<Task> findByProjectAndPriority(Project project, String priority);

    List<Task> findByProjectAndCompleted(Project project, boolean completed);
}
