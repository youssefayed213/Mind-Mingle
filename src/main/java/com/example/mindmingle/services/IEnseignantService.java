package com.example.mindmingle.services;

import com.example.mindmingle.entities.Enseignant;

import java.util.List;

public interface IEnseignantService {
    Enseignant saveEnseignant(Enseignant enseignant);
    Enseignant updateEnseignant(Enseignant enseignant);
    void deleteEnseignant(Integer id);
    Enseignant getEnseignantById(Integer id);
    List<Enseignant> getAllEnseignants();

    void assignClasseToEnseignant(Integer idEnseignant, Integer idClasse);

}
