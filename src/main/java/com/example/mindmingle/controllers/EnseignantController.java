package com.example.mindmingle.controllers;


import com.example.mindmingle.entities.Classe;
import com.example.mindmingle.entities.Enseignant;
import com.example.mindmingle.entities.Expert;
import com.example.mindmingle.services.IEnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enseignants")
public class EnseignantController {

    @Autowired
    private IEnseignantService enseignantService;

    @PostMapping("/add")
    public Enseignant addEnseignant(@RequestBody Enseignant enseignant) {
        return enseignantService.saveEnseignant(enseignant);
    }

    @PutMapping("/update")
    public Enseignant updateEnseignant(@RequestBody Enseignant enseignant) {
        return enseignantService.updateEnseignant(enseignant);
    }

    @DeleteMapping("/delete/{id}")
    public void removeEnseignant(@PathVariable Integer idEnseignant) {
        enseignantService.deleteEnseignant(idEnseignant);
    }

    @GetMapping("/{id}")
    public Enseignant getClasseById(@PathVariable Integer idEnseignant) {
        return enseignantService.getEnseignantById(idEnseignant);
    }

    @GetMapping("/retrieve-all")
    public List<Enseignant> retrieveAllEnseignant() {
        return enseignantService.getAllEnseignants();
    }

    @PostMapping("/{idEnseignant}/assign-classe/{idClasse}")
    public void assignEnseignantToClasse(@PathVariable Integer idEnseignant, @PathVariable Integer idClasse) {
        enseignantService.assignClasseToEnseignant(idEnseignant, idClasse);
    }


}
