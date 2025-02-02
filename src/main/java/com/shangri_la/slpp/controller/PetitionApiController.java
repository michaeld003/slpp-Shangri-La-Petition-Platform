package com.shangri_la.slpp.controller;

import com.shangri_la.slpp.entity.Petition;
import com.shangri_la.slpp.service.PetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slpp")
@RequiredArgsConstructor
public class PetitionApiController {

    private final PetitionService petitionService;

    @GetMapping("/petitions")
    public ResponseEntity<Map<String, Object>> getAllPetitions(
            @RequestParam(required = false) String status) {
        List<Petition> petitions;

        if (status != null && status.equalsIgnoreCase("open")) {
            petitions = petitionService.getOpenPetitions();
        } else {
            petitions = petitionService.getAllPetitions();
        }

        List<Map<String, Object>> petitionsList = petitions.stream()
                .map(this::convertPetitionToMap)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("petitions", petitionsList);

        return ResponseEntity.ok(response);
    }

    private Map<String, Object> convertPetitionToMap(Petition petition) {
        Map<String, Object> petitionMap = new HashMap<>();
        petitionMap.put("petition_id", petition.getId().toString());
        petitionMap.put("status", petition.getStatus().toString().toLowerCase());
        petitionMap.put("petition_title", petition.getTitle());
        petitionMap.put("petition_text", petition.getContent());
        petitionMap.put("petitioner", petition.getCreator().getEmail());
        petitionMap.put("signatures", String.valueOf(petition.getSignatureCount()));
        petitionMap.put("response", petition.getResponse() != null ? petition.getResponse() : "");
        return petitionMap;
    }
}