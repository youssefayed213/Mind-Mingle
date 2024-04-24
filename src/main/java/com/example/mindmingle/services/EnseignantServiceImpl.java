package com.example.mindmingle.services;


import com.example.mindmingle.entities.CategoryEtudiant;
import com.example.mindmingle.entities.Classe;
import com.example.mindmingle.entities.Enseignant;
import com.example.mindmingle.entities.Etudiant;
import com.example.mindmingle.repositories.ClasseRepository;
import com.example.mindmingle.repositories.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnseignantServiceImpl implements IEnseignantService{

    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private ClasseRepository classeRepository;


    @Override
    public Enseignant saveEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    @Override
    public Enseignant updateEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    @Override
    public void deleteEnseignant(Integer id) {
        enseignantRepository.deleteById(id);
    }

    @Override
    public Enseignant getEnseignantById(Integer id) {
        Optional<Enseignant> optionalEnseignant = enseignantRepository.findById(id);
        return optionalEnseignant.orElse(null);
    }

    @Override
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }
    @Override
    public void assignClasseToEnseignant(Integer idEnseignant, Integer idClasse) {
        Enseignant enseignant = enseignantRepository.findById(idEnseignant).orElseThrow(() -> new IllegalArgumentException("Enseignant not found"));
        Classe classe = classeRepository.findById(idClasse).orElseThrow(() -> new IllegalArgumentException("Classe not found"));
        enseignant.getClasseAEnseigner().add(classe);
        classe.getEnseignants().add(enseignant );
        enseignantRepository.save(enseignant);
        classeRepository.save(classe);
    }

}
