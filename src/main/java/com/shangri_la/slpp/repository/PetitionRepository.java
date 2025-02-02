package com.shangri_la.slpp.repository;

import com.shangri_la.slpp.entity.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Long> {
    List<Petition> findByStatus(Petition.PetitionStatus status);
    List<Petition> findByCreatorId(Long creatorId);
    boolean existsByIdAndSignaturesId(Long petitionId, Long userId);

    @Query("SELECT DISTINCT p FROM Petition p " +
            "LEFT JOIN FETCH p.creator " +
            "LEFT JOIN FETCH p.signatures " +
            "WHERE p.id = :id")
    Optional<Petition> findByIdWithDetails(@Param("id") Long id);
}