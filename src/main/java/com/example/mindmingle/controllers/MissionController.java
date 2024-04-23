package com.example.mindmingle.controllers;


import com.example.mindmingle.entities.Mission;
import com.example.mindmingle.services.IMissionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missions")
public class MissionController {



    @Autowired
    private IMissionInterface missionService;

    @PostMapping("/addMission")
    public Mission addMission(@RequestBody Mission mission) {
        return missionService.addMission(mission);
    }

    @PutMapping("/update/{idMission}")
    public Mission updateMission(@RequestBody Mission mission) {
        return missionService.updateMission(mission);
    }

    @DeleteMapping("/delete/{idMission}")
    public void removeMission(@PathVariable Integer idMission) {
        missionService.removeMission(idMission);
    }

    @GetMapping("/retrieve-all")
    public List<Mission> retrieveAllMission() {
        return missionService.retrieveAllMission();
    }
}
