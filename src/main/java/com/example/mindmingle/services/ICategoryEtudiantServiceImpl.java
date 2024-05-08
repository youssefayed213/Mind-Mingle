package com.example.mindmingle.services;


import com.example.mindmingle.entities.CategoryEtudiant;
import com.example.mindmingle.entities.Objective;
import com.example.mindmingle.repositories.CategoryEtudiantRepository;
import com.example.mindmingle.repositories.ObjectiveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ICategoryEtudiantServiceImpl implements ICategoryEtudiantService{

    @Autowired
    private CategoryEtudiantRepository categoryEtudiantRepository;

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Override
    public CategoryEtudiant addCategoryEtudiant(CategoryEtudiant categoryEtudiant) {
        return categoryEtudiantRepository.save(categoryEtudiant);
    }

    @Override
    public CategoryEtudiant updateCategoryEtudiant(CategoryEtudiant categoryEtudiant) {
        return categoryEtudiantRepository.save(categoryEtudiant);
    }

    @Override
    public void removeCategoryEtudiant(Integer idCategorie) {
        categoryEtudiantRepository.deleteById(idCategorie);
    }

    @Override
    public List<CategoryEtudiant> retrieveAllCategoriesEtudiant() {
        return categoryEtudiantRepository.findAll();
    }

    @Override
    public CategoryEtudiant getCategoryEtudiantById(Integer idCategorie) {
        return categoryEtudiantRepository.findById(idCategorie)
                .orElseThrow(() -> new IllegalArgumentException("Category Etudiant not found"));
    }


    @Override
    public void assignObjectiveToCategory(Integer idObjective, Integer idCategoryEtudiant) {
        // Retrieve the Objective and CategoryEtudiant entities from their respective repositories
        Objective objective = objectiveRepository.findById(idObjective)
                .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + idObjective));
        CategoryEtudiant categoryEtudiant = categoryEtudiantRepository.findById(idCategoryEtudiant)
                .orElseThrow(() -> new EntityNotFoundException("CategoryEtudiant not found with id: " + idCategoryEtudiant));

        // Assign the Objective to the CategoryEtudiant
        objective.setCategoryEtudiant(categoryEtudiant);
        objectiveRepository.save(objective);
    }

    @Override
    public Optional<CategoryEtudiant> retriveCategorieById(Integer idCategorie) {
        return categoryEtudiantRepository.findById(idCategorie);
    }
}
