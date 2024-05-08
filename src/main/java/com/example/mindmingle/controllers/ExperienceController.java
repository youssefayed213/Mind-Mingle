package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Experience;
import com.example.mindmingle.services.IExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiences")
public class ExperienceController {
    @Autowired
    private IExperienceService experienceService;

    @PostMapping("/add")
    public Experience addExperience(@RequestBody Experience experience) {
        return experienceService.addExperience(experience);
    }

    @PutMapping("/update")
    public Experience updateExperience(@RequestBody Experience experience) {
        return experienceService.updateExperience(experience);
    }

    @DeleteMapping("/delete/{idExperience}")
    public void removeExperience(@PathVariable Integer idExperience) {
        experienceService.removeExperience(idExperience);
    }

    @GetMapping("/retrieve-all")
    public List<Experience> retrieveAllExperience() {
        return experienceService.retrieveAllExperience();
    }

    @GetMapping("/{idExperience}")
    public Experience getExperienceById(@PathVariable Integer idExperience) {
        return experienceService.getExperienceById(idExperience);
    }
}
