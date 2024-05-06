package com.example.mindmingle.services;

import com.example.mindmingle.entities.CategorieGroupe;

import java.util.List;

public interface ICategorieGroupe {
    public CategorieGroupe addCatGr(CategorieGroupe categorieGroupe);
    public CategorieGroupe upadateCatGr(CategorieGroupe categorieGroupe);
    void removeCatGr(Integer idCatGroupe);
    public CategorieGroupe retrieveCatGr(Integer idCatGroupe);
    public List<CategorieGroupe> retrieveAllCatGr();
}
