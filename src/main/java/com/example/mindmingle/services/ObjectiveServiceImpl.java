package com.example.mindmingle.services;

import com.example.mindmingle.entities.Mission;
import com.example.mindmingle.entities.Objective;
import com.example.mindmingle.repositories.MissionRepository;
import com.example.mindmingle.repositories.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectiveServiceImpl implements IObjectiveService{
    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Override
    public Objective addObjective(Objective objective) {
        return objectiveRepository.save(objective);
    }

    @Override
    public Objective updateObjective(Objective objective) {
        return objectiveRepository.save(objective);
    }

    @Override
    public void removeObjective(Integer idObjective) {
        objectiveRepository.deleteById(idObjective);
    }

    @Override
    public List<Objective> retrieveAllObjective() {
        return objectiveRepository.findAll();
    }

    @Override
    public void addMissionToObjective(Integer idMission, Integer idObjective) {
        Mission mission = missionRepository.findById(idMission).orElseThrow(() -> new IllegalArgumentException("Mission not found"));
        Objective objective = objectiveRepository.findById(idObjective).orElseThrow(() -> new IllegalArgumentException("Objective not found"));
        mission.setObjective(objective);
        missionRepository.save(mission);
    }
    @Override
    public List<Objective> findByCategoryEtudiant_IdCategorie(Integer idCategoryEtudiant) {
        return objectiveRepository.findByCategoryEtudiant_IdCategorie(idCategoryEtudiant);
    }
    @Override
    public Optional<Objective> retriveObjectiveById(Integer idObjective) {
        return objectiveRepository.findById(idObjective);
    }

}
