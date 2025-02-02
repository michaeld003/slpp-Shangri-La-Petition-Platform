package com.shangri_la.slpp.service;

import com.shangri_la.slpp.dto.RegisterRequest;
import com.shangri_la.slpp.entity.User;
import com.shangri_la.slpp.entity.ValidBioId;
import com.shangri_la.slpp.repository.UserRepository;
import com.shangri_la.slpp.repository.ValidBioIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ValidBioIdRepository validBioIdRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Validate and mark BioID as used
        ValidBioId validBioId = validBioIdRepository.findByBioidAndIsUsedFalse(request.getBioid())
                .orElseThrow(() -> new RuntimeException("Invalid or already used BioID"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setBioid(request.getBioid());
        user.setRole(User.UserRole.PETITIONER);

        // Mark BioID as used
        validBioId.setIsUsed(true);
        validBioIdRepository.save(validBioId);

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}