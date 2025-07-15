package com.example.pms.repository;

import com.example.pms.entity.Project;
import com.example.pms.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByUser(User user);
}
