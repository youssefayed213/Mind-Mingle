package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Expert;
import com.example.mindmingle.services.IExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/experts")
public class ExpertController {
    @Autowired
    private IExpertService expertService;

    @PostMapping("/add")
    public Expert addExpert(@RequestPart("expert") Expert expert,
                            @RequestParam(value = "dossierFile", required = false) MultipartFile dossierFile) {
        return expertService.addExpert(expert, dossierFile);
    }

    @PutMapping("/update")
    public Expert updateExpert(@RequestBody Expert expert) {
        return expertService.updateExpert(expert);
    }

    @DeleteMapping("/delete/{idExpert}")
    public void removeExpert(@PathVariable Integer idExpert) {
        expertService.removeExpert(idExpert);
    }

    @GetMapping("/retrieve-all")
    public List<Expert> retrieveAllExperts() {
        return expertService.retrieveAllExperts();
    }

    @PostMapping("/{idExpert}/assign-experience/{idExperience}")
    public void assignExperienceToExpert(@PathVariable Integer idExpert, @PathVariable Integer idExperience) {
        expertService.assignExperienceToExpert(idExpert, idExperience);
    }
}
