package com.example.mindmingle.controllers;


import com.example.mindmingle.entities.Etudiant;
import com.example.mindmingle.services.IEtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private IEtudiantService etudiantService;

    @PostMapping("/add")
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.addEtudiant(etudiant);
    }

    @PutMapping("/update")
    public Etudiant updateEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.updateEtudiant(etudiant);
    }

    @DeleteMapping("/delete/{idEtudiant}")
    public void removeEtudiant(@PathVariable Integer idEtudiant) {
        etudiantService.removeEtudiant(idEtudiant);
    }

    @GetMapping("/retrieve-all")
    public List<Etudiant> retrieveAllEtudiants() {
        return etudiantService.retrieveAllEtudiants();
    }



    @GetMapping("/{idEtudiant}")
    public Etudiant getEtudiantById(@PathVariable Integer idEtudiant) {
        return etudiantService.getEtudiantById(idEtudiant);
    }

    @PostMapping("/{idEtudiant}/assign-classe/{idClasse}")
    public void assignEtudiantToClasse(@PathVariable Integer idEtudiant, @PathVariable Integer idClasse) {
        etudiantService.assignEtudiantToClasse(idEtudiant, idClasse);
    }

    @PostMapping("/{idEtudiant}/assign-category/{idCategoryEtudiant}")
    public void assignEtudiantToCategory(@PathVariable Integer idEtudiant, @PathVariable Integer idCategoryEtudiant) {
        etudiantService.assignEtudiantToCategory(idEtudiant, idCategoryEtudiant);
    }
    @PostMapping("/ajouter")
    public Etudiant addEtudiant(@RequestPart("etudiant") Etudiant etudiant,
                              @RequestParam(value = "dossierFile", required = false) MultipartFile dossierFile,
                              @RequestParam(value = "picture", required = false) MultipartFile picture) {
        return etudiantService.ajouterEtudiant(etudiant, dossierFile,picture);
}

}
