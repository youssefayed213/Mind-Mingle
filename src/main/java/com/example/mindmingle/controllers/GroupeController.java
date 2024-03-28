package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.services.IGroupe;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/Groupe")
public class GroupeController {
    IGroupe groupeService; //injection des dépendances
    @GetMapping("/getGroupe/{IdGroupe}")
    public Groupe getGroupe(@PathVariable("IdGroupe") Integer IdGroupe) {
        return groupeService.retrieveGroupe(IdGroupe);
    }
    @GetMapping("/getAllGroupe")
    public List<Groupe> getAllGroupes() {
        return groupeService.retrieveAllGroupe();
    }
    @PostMapping("/addGroupe")
    public  Groupe addGroupe(@RequestBody Groupe groupe){
        return groupeService.addGroupe(groupe);
    }
    @PutMapping("/updateGroupe")
    public Groupe updateGroupe(@RequestBody Groupe groupe){
        return groupeService.upadateGroupe(groupe);
    }
    @DeleteMapping("/deleteGroupe/{IdGroupe}")
    public void removeGroupe(@PathVariable ("IdGroupe") Integer IdGroupe){
        groupeService.removeGroupe(IdGroupe);
    }
}

