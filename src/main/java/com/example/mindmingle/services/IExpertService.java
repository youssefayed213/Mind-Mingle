package com.example.mindmingle.services;

import com.example.mindmingle.entities.Expert;

import java.util.List;

public interface IExpertService {

    Expert addExpert(Expert expert);
    Expert updateExpert(Expert expert);
    void removeExpert(Integer idExpert);
    List<Expert> retrieveAllExperts();
    void assignExperienceToExpert(Integer idExpert, Integer idExperience);
}
