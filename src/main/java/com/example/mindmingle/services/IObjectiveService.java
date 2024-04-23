package com.example.mindmingle.services;

import com.example.mindmingle.entities.Objective;

import java.util.List;

public interface IObjectiveService {
    Objective addObjective(Objective objective);
    Objective updateObjective(Objective objective);
    void removeObjective(Integer idObjective);
    List<Objective> retrieveAllObjective();
    void addMissionToObjective(Integer idMission, Integer idObjective);
}

