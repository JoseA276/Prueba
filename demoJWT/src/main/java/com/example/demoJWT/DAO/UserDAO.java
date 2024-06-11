package com.example.demoJWT.DAO;

import com.example.demoJWT.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    List<User> findAll();
    Optional<User> findByEmail(String email);
}
