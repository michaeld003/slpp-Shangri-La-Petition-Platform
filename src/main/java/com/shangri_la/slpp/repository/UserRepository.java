package com.shangri_la.slpp.repository;

import com.shangri_la.slpp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByBioid(String bioid);
    boolean existsByEmail(String email);
    boolean existsByBioid(String bioid);
}