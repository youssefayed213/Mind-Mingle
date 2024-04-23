package com.example.mindmingle.services;

import com.example.mindmingle.entities.Etudiant;

import java.util.List;

public interface IEtudiantService {

    Etudiant addEtudiant(Etudiant etudiant);
    Etudiant updateEtudiant(Etudiant etudiant);
    void removeEtudiant(Integer idEtudiant);
    List<Etudiant> retrieveAllEtudiants();
    Etudiant getEtudiantById(Integer idEtudiant);

    void assignEtudiantToClasse(Integer idEtudiant, Integer idClasse);
    void assignEtudiantToCategory(Integer idEtudiant, Integer idCategoryEtudiant);
}
