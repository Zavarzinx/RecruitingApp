package com.recruiting.repo;

import com.recruiting.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsUserByUsername(String username);

    User findByEmail(String email);
}
