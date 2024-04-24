package com.example.mindmingle.services;

import com.example.mindmingle.entities.Expert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IExpertService {

    Expert addExpert(Expert expert);
    Expert updateExpert(Expert expert);
    void removeExpert(Integer idExpert);
    List<Expert> retrieveAllExperts();
    void assignExperienceToExpert(Integer idExpert, Integer idExperience);

    Expert addExpert(Expert expert, MultipartFile dossierFile);
}
