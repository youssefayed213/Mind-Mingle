package com.example.mindmingle.services;

import com.example.mindmingle.entities.CategoryEtudiant;

import java.util.List;
import java.util.Optional;

public interface ICategoryEtudiantService {

    CategoryEtudiant addCategoryEtudiant(CategoryEtudiant categoryEtudiant);
    CategoryEtudiant updateCategoryEtudiant(CategoryEtudiant categoryEtudiant);
    void removeCategoryEtudiant(Integer idCategorie);
    List<CategoryEtudiant> retrieveAllCategoriesEtudiant();
    CategoryEtudiant getCategoryEtudiantById(Integer idCategorie);

    void assignObjectiveToCategory(Integer idObjective, Integer idCategoryEtudiant);
    Optional<CategoryEtudiant> retriveCategorieById(Integer idCategorie);

}
