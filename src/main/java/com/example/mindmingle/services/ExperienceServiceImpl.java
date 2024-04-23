package com.example.mindmingle.services;

import com.example.mindmingle.entities.Experience;
import com.example.mindmingle.repositories.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements IExperienceService{

    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public Experience addExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public Experience updateExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public void removeExperience(Integer idExperience) {
        experienceRepository.deleteById(idExperience);
    }

    @Override
    public List<Experience> retrieveAllExperience() {
        return experienceRepository.findAll();
    }

    @Override
    public Experience getExperienceById(Integer idExperience) {
        return experienceRepository.findById(idExperience)
                .orElseThrow(() -> new IllegalArgumentException("Experience not found"));
    }
}
