package com.example.mindmingle.services;


import com.example.mindmingle.entities.Experience;
import com.example.mindmingle.entities.Expert;
import com.example.mindmingle.repositories.ExperienceRepository;
import com.example.mindmingle.repositories.ExpertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExpertServiceImpl implements IExpertService{
    @Autowired
    private ExpertRepository expertRepository;
    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public Expert addExpert(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public Expert updateExpert(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public void removeExpert(Integer idExpert) {
        expertRepository.deleteById(idExpert);
    }

    @Override
    public List<Expert> retrieveAllExperts() {
        return expertRepository.findAll();
    }
    @Override
    public void assignExperienceToExpert(Integer idExpert, Integer idExperience) {
        Expert expert = expertRepository.findById(idExpert)
                .orElseThrow(() -> new IllegalArgumentException("Expert not found"));

        Experience experience = experienceRepository.findById(idExperience)
                .orElseThrow(() -> new IllegalArgumentException("Experience not found"));

        // Assign the experience to the expert
        expert.getExperiences().add(experience);
        experience.setExpert(expert);

        // Save changes
        expertRepository.save(expert);
    }


    @Override
    public Expert addExpert(Expert expert, MultipartFile dossierFile) {
        try {
            if (dossierFile != null) {
                expert.setDossierContent(dossierFile.getBytes());
            }
            return expertRepository.save(expert);
        } catch (IOException ex) {
            // Handle IOException
            throw new RuntimeException("Failed to save dossier file", ex);
        }
    }
}
