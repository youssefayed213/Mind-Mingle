package com.example.mindmingle.services;

import com.example.mindmingle.entities.Classe;

import java.util.List;

public interface IClasseService {

    Classe addClasse(Classe classe);
    Classe updateClasse(Classe classe);
    void removeClasse(Integer idClasse);
    List<Classe> retrieveAllClasses();
    Classe getClasseById(Integer idClasse);

}
