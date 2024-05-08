package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.CategoryEtudiant;
import com.example.mindmingle.services.ICategoryEtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/categories-etudiant")
public class CategoryEtudiantController {

    @Autowired
    private ICategoryEtudiantService categoryEtudiantService;

    @PostMapping("/add")
    public CategoryEtudiant addCategoryEtudiant(@RequestBody CategoryEtudiant categoryEtudiant) {
        return categoryEtudiantService.addCategoryEtudiant(categoryEtudiant);
    }

    @PutMapping("/update")
    public CategoryEtudiant updateCategoryEtudiant(@RequestBody CategoryEtudiant categoryEtudiant) {
        return categoryEtudiantService.updateCategoryEtudiant(categoryEtudiant);
    }

    @DeleteMapping("/delete/{idCategorie}")
    public void removeCategoryEtudiant(@PathVariable Integer idCategorie) {
        categoryEtudiantService.removeCategoryEtudiant(idCategorie);
    }

    @GetMapping("/retrieve-all")
    public List<CategoryEtudiant> retrieveAllCategoriesEtudiant() {
        return categoryEtudiantService.retrieveAllCategoriesEtudiant();
    }

    @GetMapping("/{idCategorie}")
    public CategoryEtudiant getCategoryEtudiantById(@PathVariable Integer idCategorie) {
        return categoryEtudiantService.getCategoryEtudiantById(idCategorie);
    }


    @PostMapping("/{idCategoryEtudiant}/assign-objective-to-Category/{idObjective}")
    public ResponseEntity<String> assignObjectiveToCategory(@PathVariable Integer idCategoryEtudiant,
                                                            @PathVariable Integer idObjective) {
        categoryEtudiantService.assignObjectiveToCategory(idObjective, idCategoryEtudiant);
        return ResponseEntity.ok("Objective assigned to CategoryEtudiant successfully.");
    }

    @CrossOrigin
    @GetMapping("/retrieve/{idCategorie}")
    public Optional<CategoryEtudiant> retrieveCategoryById(@PathVariable Integer idCategorie) {
        return categoryEtudiantService.retriveCategorieById(idCategorie);
    }

}
