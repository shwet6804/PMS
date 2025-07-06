package com.example.pms.repository;

import com.example.pms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ This must return User directly, not Optional<User>
    User findByEmail(String email);
}
