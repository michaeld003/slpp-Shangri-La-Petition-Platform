package com.shangri_la.slpp.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.shangri_la.slpp.entity.User;
import com.shangri_la.slpp.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Component
public class AdminInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        String adminEmail = "admin@petition.parliament.sr";


        userRepository.findByEmail(adminEmail).ifPresentOrElse(
                existingAdmin -> {

                    String encodedPassword = passwordEncoder.encode("2025%shangrila");
                    existingAdmin.setPasswordHash(encodedPassword);
                    userRepository.save(existingAdmin);
                    System.out.println("Admin password updated successfully");
                },
                () -> {
                    // Create new admin if doesn't exist
                    User admin = new User();
                    admin.setEmail(adminEmail);
                    admin.setFullName("System Administrator");
                    admin.setDateOfBirth(LocalDate.of(2000, 1, 1));
                    String encodedPassword = passwordEncoder.encode("2025%shangrila");
                    System.out.println("Generated admin password hash: " + encodedPassword);
                    admin.setPasswordHash(encodedPassword);
                    admin.setRole(User.UserRole.COMMITTEE);
                    userRepository.save(admin);
                    System.out.println("Admin user created successfully");
                }
        );
    }
}