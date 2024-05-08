package com.example.mindmingle.services;

import com.example.mindmingle.entities.Mission;
import com.example.mindmingle.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionServiceImpl implements IMissionInterface {
    @Autowired
    private MissionRepository missionRepository;

    @Override
    public Mission addMission(Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public Mission updateMission(Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public void removeMission(Integer idMission) {
        missionRepository.deleteById(idMission);
    }

    @Override
    public List<Mission> retrieveAllMission() {
        return missionRepository.findAll();
    }
    @Override
    public List<Mission> getAllMissionsByObjective(Integer idObjective) {
        return missionRepository.findByObjective_IdObjective(idObjective);
    }
}
