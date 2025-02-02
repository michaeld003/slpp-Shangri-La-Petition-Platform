package com.shangri_la.slpp.controller;

import com.shangri_la.slpp.service.PetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/committee")
@RequiredArgsConstructor
public class CommitteeController {

    private final PetitionService petitionService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        System.out.println("Loading committee dashboard...");
        model.addAttribute("petitions", petitionService.getAllPetitions());
        model.addAttribute("currentThreshold", 100); // TODO: Get from service
        return "committee/dashboard";
    }

    @PostMapping("/petition/{id}/respond")
    public String respondToPetition(@PathVariable Long id,
                                    @RequestParam String response) {
        petitionService.respondToPetition(id, response);
        return "redirect:/committee/dashboard";
    }

    @PostMapping("/threshold")
    public String setSignatureThreshold(@RequestParam Integer threshold) {
        // TODO: Implement signature threshold setting
        return "redirect:/committee/dashboard";
    }
}