package com.example.mindmingle.services;

import com.example.mindmingle.entities.ProfilEtudiant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface IProfilEtudiantService {
    ProfilEtudiant addProfil(ProfilEtudiant pe);
    ProfilEtudiant updateProfil(ProfilEtudiant pe);
    void removeProfil(Integer idProfil);
    ProfilEtudiant retrieveProfil(Integer idProfil);
    List<ProfilEtudiant> retreiveAllProfil();
}
