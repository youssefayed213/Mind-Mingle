package com.example.mindmingle.services;


import com.example.mindmingle.entities.Classe;
import com.example.mindmingle.repositories.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IClasseServiceImpl implements IClasseService{
    @Autowired
    private ClasseRepository classeRepository;

    @Override
    public Classe addClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public Classe updateClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public void removeClasse(Integer idClasse) {
        classeRepository.deleteById(idClasse);
    }

    @Override
    public List<Classe> retrieveAllClasses() {
        return classeRepository.findAll();
    }

    @Override
    public Classe getClasseById(Integer idClasse) {
        return classeRepository.findById(idClasse)
                .orElseThrow(() -> new IllegalArgumentException("Classe not found"));
    }
}
