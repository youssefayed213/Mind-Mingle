package com.example.mindmingle.controllers;


import com.example.mindmingle.entities.Objective;
import com.example.mindmingle.services.IObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/objectives")
public class ObjectiveController {

    @Autowired
    private IObjectiveService objectiveService;

    @PostMapping("/addObjective")
    public Objective addObjective(@RequestBody Objective objective) {
        return objectiveService.addObjective(objective);
    }

    @PutMapping("/update/{idObjective}")
    public Objective updateObjective(@RequestBody Objective objective) {
        return objectiveService.updateObjective(objective);
    }

    @DeleteMapping("/delete/{idObjective}")
    public void removeObjective(@PathVariable Integer idObjective) {
        objectiveService.removeObjective(idObjective);
    }

    @GetMapping("/retrieve-all")
    public List<Objective> retrieveAllObjective() {
        return objectiveService.retrieveAllObjective();
    }

    @PostMapping("/addMissionToObjective/{idMission}/{idObjective}")
    public void addMissionToObjective(@PathVariable Integer idMission, @PathVariable Integer idObjective) {
        objectiveService.addMissionToObjective(idMission, idObjective);
    }
    @GetMapping("/getByCategory/{idCategoryEtudiant}")
    public List<Objective> getObjectivesByCategory(@PathVariable Integer idCategoryEtudiant) {
        return objectiveService.findByCategoryEtudiant_IdCategorie(idCategoryEtudiant);
    }

    @CrossOrigin
    @GetMapping("/retrieve/{idObjective}")
    public Optional<Objective> retrieveObjectiveById(@PathVariable Integer idObjective) {
        return objectiveService.retriveObjectiveById(idObjective);
    }
}
