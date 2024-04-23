package com.example.mindmingle.services;

import com.example.mindmingle.entities.Mission;

import java.util.List;

public interface IMissionInterface {
    Mission addMission(Mission mission);
    Mission updateMission(Mission mission);
    void removeMission(Integer idMission);
    List<Mission> retrieveAllMission();
}
