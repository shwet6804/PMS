package com.example.pms.repository;

import com.example.pms.entity.Project;
import com.example.pms.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findByProject(Project project);

    List<Task> findByProjectAndPriority(Project project, String priority);

    List<Task> findByProjectAndCompleted(Project project, boolean completed);
}
    