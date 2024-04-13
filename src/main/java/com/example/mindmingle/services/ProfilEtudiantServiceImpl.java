package com.example.mindmingle.services;

import com.example.mindmingle.entities.ProfilEtudiant;
import com.example.mindmingle.repositories.ProfilEtudiantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfilEtudiantServiceImpl implements IProfilEtudiantService {
ProfilEtudiantRepository profilEtudiantRepository;
    @Override
    public ProfilEtudiant addProfil(ProfilEtudiant pe) {
        return profilEtudiantRepository.save(pe);
    }

    @Override
    public ProfilEtudiant updateProfil(ProfilEtudiant pe) {
        return profilEtudiantRepository.save(pe);

    }

    @Override
    public void removeProfil(Integer idProfil) {
        profilEtudiantRepository.deleteById(idProfil);

    }

    @Override
    public ProfilEtudiant retrieveProfil(Integer idProfil) {
        return profilEtudiantRepository.findById( idProfil).orElse(null);
    }

    @Override
    public List<ProfilEtudiant> retreiveAllProfil() {
        return profilEtudiantRepository.findAll();


    }
}
