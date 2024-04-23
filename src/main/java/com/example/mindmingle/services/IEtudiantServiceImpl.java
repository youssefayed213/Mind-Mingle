package com.example.mindmingle.services;

import com.example.mindmingle.entities.CategoryEtudiant;
import com.example.mindmingle.entities.Classe;
import com.example.mindmingle.entities.Etudiant;
import com.example.mindmingle.repositories.CategoryEtudiantRepository;
import com.example.mindmingle.repositories.ClasseRepository;
import com.example.mindmingle.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IEtudiantServiceImpl implements IEtudiantService{

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private CategoryEtudiantRepository categoryEtudiantRepository;

    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant updateEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public void removeEtudiant(Integer idEtudiant) {
        etudiantRepository.deleteById(idEtudiant);
    }

    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant getEtudiantById(Integer idEtudiant) {
        return etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new IllegalArgumentException("Etudiant not found"));
    }

    @Override
    public void assignEtudiantToClasse(Integer idEtudiant, Integer idClasse) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new IllegalArgumentException("Etudiant not found"));
        Classe classe = classeRepository.findById(idClasse)
                .orElseThrow(() -> new IllegalArgumentException("Classe not found"));
        etudiant.setClasse(classe);
        etudiantRepository.save(etudiant);
    }

    @Override
    public void assignEtudiantToCategory(Integer idEtudiant, Integer idCategoryEtudiant) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new IllegalArgumentException("Etudiant not found"));
        CategoryEtudiant categoryEtudiant = categoryEtudiantRepository.findById(idCategoryEtudiant)
                .orElseThrow(() -> new IllegalArgumentException("CategoryEtudiant not found"));
        etudiant.setCategoryEtudiant(categoryEtudiant);
        etudiantRepository.save(etudiant);
    }
}
