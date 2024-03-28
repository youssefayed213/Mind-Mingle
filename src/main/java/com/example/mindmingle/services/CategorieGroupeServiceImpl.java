package com.example.mindmingle.services;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.repositories.CategorieGroupeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CategorieGroupeServiceImpl implements ICategorieGroupe{
    CategorieGroupeRepository categorieGroupeRepository;
    @Override
    public CategorieGroupe addCatGr(CategorieGroupe categorieGroupe) {
        return categorieGroupeRepository.save(categorieGroupe);
    }

    @Override
    public CategorieGroupe upadateCatGr(CategorieGroupe categorieGroupe) {
        return categorieGroupeRepository.save(categorieGroupe);
    }

    @Override
    public void removeCatGr(Integer idCatGroupe) {
        categorieGroupeRepository.deleteById(idCatGroupe);
    }

    @Override
    public CategorieGroupe retrieveCatGr(Integer idCatGroupe) {
        return categorieGroupeRepository.findById(idCatGroupe).get();
    }

    @Override
    public List<CategorieGroupe> retrieveAllCatGr() {
        return categorieGroupeRepository.findAll();
    }
}
