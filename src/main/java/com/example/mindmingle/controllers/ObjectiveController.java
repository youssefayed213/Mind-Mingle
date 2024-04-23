package com.example.mindmingle.controllers;


import com.example.mindmingle.entities.Objective;
import com.example.mindmingle.services.IObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
}

