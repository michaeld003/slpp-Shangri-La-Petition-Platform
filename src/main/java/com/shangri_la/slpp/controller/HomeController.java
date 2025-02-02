package com.shangri_la.slpp.controller;

import com.shangri_la.slpp.service.PetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PetitionService petitionService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        // Only redirect committee members to their dashboard
        if (authentication != null &&
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COMMITTEE"))) {
            return "redirect:/committee/dashboard";
        }

        // For everyone else (authenticated users and visitors), show the petitions
        model.addAttribute("petitions", petitionService.getAllPetitions());
        return "home";
    }
}