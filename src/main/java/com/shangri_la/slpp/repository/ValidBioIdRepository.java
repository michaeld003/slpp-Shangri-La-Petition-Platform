package com.shangri_la.slpp.repository;

import com.shangri_la.slpp.entity.ValidBioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidBioIdRepository extends JpaRepository<ValidBioId, String> {
    Optional<ValidBioId> findByBioidAndIsUsedFalse(String bioid);
}