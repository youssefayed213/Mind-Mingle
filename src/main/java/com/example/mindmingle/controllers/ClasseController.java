package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Classe;
import com.example.mindmingle.services.IClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClasseController {

    @Autowired
    private IClasseService classeService;

    @PostMapping("/add")
    public Classe addClasse(@RequestBody Classe classe) {
        return classeService.addClasse(classe);
    }

    @PutMapping("/update")
    public Classe updateClasse(@RequestBody Classe classe) {
        return classeService.updateClasse(classe);
    }

    @DeleteMapping("/delete/{idClasse}")
    public void removeClasse(@PathVariable Integer idClasse) {
        classeService.removeClasse(idClasse);
    }

    @GetMapping("/retrieve-all")
    public List<Classe> retrieveAllClasses() {
        return classeService.retrieveAllClasses();
    }

    @GetMapping("/{idClasse}")
    public Classe getClasseById(@PathVariable Integer idClasse) {
        return classeService.getClasseById(idClasse);
    }
}
