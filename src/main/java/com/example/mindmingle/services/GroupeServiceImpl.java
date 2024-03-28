package com.example.mindmingle.services;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.repositories.CategorieGroupeRepository;
import com.example.mindmingle.repositories.GroupeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class GroupeServiceImpl implements IGroupe{
    GroupeRepository groupeRepository;
    @Override
    public Groupe addGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public Groupe upadateGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public void removeGroupe(Integer idGroupe) {
        groupeRepository.deleteById(idGroupe);
    }

    @Override
    public Groupe retrieveGroupe(Integer idGroupe) {
        return groupeRepository.findById(idGroupe).get();
    }

    @Override
    public List<Groupe> retrieveAllGroupe() {
         return groupeRepository.findAll();
    }
}
