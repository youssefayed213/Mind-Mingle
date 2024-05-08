package com.example.mindmingle.services;

import com.example.mindmingle.entities.Experience;

import java.util.List;

public interface IExperienceService {

    Experience addExperience(Experience experience);
    Experience updateExperience(Experience experience);
    void removeExperience(Integer idExperience);
    List<Experience> retrieveAllExperience();
    Experience getExperienceById(Integer idExperience);
}
