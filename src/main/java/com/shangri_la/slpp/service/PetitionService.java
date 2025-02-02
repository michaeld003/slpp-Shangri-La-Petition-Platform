package com.shangri_la.slpp.service;

import com.shangri_la.slpp.dto.PetitionRequest;
import com.shangri_la.slpp.entity.Petition;
import com.shangri_la.slpp.entity.User;
import com.shangri_la.slpp.repository.PetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetitionService {
    private final PetitionRepository petitionRepository;

    @Transactional
    public Petition createPetition(PetitionRequest request, User creator) {
        Petition petition = new Petition();
        petition.setTitle(request.getTitle());
        petition.setContent(request.getContent());
        petition.setCreator(creator);
        petition.setStatus(Petition.PetitionStatus.OPEN);
        return petitionRepository.save(petition);
    }

    @Transactional
    public void signPetition(Long petitionId, User user) {
        Petition petition = petitionRepository.findById(petitionId)
                .orElseThrow(() -> new RuntimeException("Petition not found"));

        if (petition.getStatus() != Petition.PetitionStatus.OPEN) {
            throw new RuntimeException("Petition is closed");
        }

        if (petitionRepository.existsByIdAndSignaturesId(petitionId, user.getId())) {
            throw new RuntimeException("Already signed this petition");
        }

        petition.getSignatures().add(user);
        petition.setSignatureCount(petition.getSignatureCount() + 1);
        petitionRepository.save(petition);
    }

    public List<Petition> getAllPetitions() {
        return petitionRepository.findAll();
    }

    public List<Petition> getOpenPetitions() {
        return petitionRepository.findByStatus(Petition.PetitionStatus.OPEN);
    }

    @Transactional
    public void respondToPetition(Long petitionId, String response) {
        Petition petition = petitionRepository.findById(petitionId)
                .orElseThrow(() -> new RuntimeException("Petition not found"));
        petition.setResponse(response);
        petition.setStatus(Petition.PetitionStatus.CLOSED);
        petitionRepository.save(petition);
    }

    @Transactional(readOnly = true)
    public Petition getPetitionById(Long id) {
        return petitionRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Petition not found"));
    }

    public List<Petition> getPetitionsByUser(Long userId) {
        return petitionRepository.findByCreatorId(userId);
    }
}