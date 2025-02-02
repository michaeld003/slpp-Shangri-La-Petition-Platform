package com.shangri_la.slpp.controller;

import com.shangri_la.slpp.dto.PetitionRequest;
import com.shangri_la.slpp.entity.Petition;
import com.shangri_la.slpp.entity.User;
import com.shangri_la.slpp.service.PetitionService;
import com.shangri_la.slpp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PetitionController {
    private final PetitionService petitionService;
    private final UserService userService;

    @GetMapping("/petition/new")
    public String newPetitionForm(Model model) {
        model.addAttribute("petitionRequest", new PetitionRequest());
        return "petition-form";
    }

    @PostMapping("/petition/new")
    public String createPetition(@Valid @ModelAttribute PetitionRequest petitionRequest,
                                 BindingResult result,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "petition-form";
        }

        User user = userService.findByEmail(userDetails.getUsername());
        petitionService.createPetition(petitionRequest, user);
        return "redirect:/dashboard";
    }

    @PostMapping("/petition/{id}/sign")
    public String signPetition(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByEmail(userDetails.getUsername());
            petitionService.signPetition(id, user);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully signed the petition!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/petition/" + id;
    }

    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable Long id, Model model) {
        Petition petition = petitionService.getPetitionById(id);
        if (petition == null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("petition", petition);
        return "petition-detail";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COMMITTEE"))) {
            return "redirect:/committee/dashboard";
        }
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("userPetitions", petitionService.getPetitionsByUser(user.getId()));
        return "dashboard";
    }
}