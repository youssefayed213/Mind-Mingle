package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.services.ICategorieGroupe;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/CatGr")
public class CategorieGroupeController {
    ICategorieGroupe catGrService; //injection des dépendances
    @GetMapping("/getCatGr/{IdCatGr}")
    public CategorieGroupe getCatGr(@PathVariable ("IdCatGr") Integer IdCatGr) {
        return catGrService.retrieveCatGr(IdCatGr);
    }
    @GetMapping("/getAllCatGr")
    public List<CategorieGroupe> getAllCatGr() {
        return catGrService.retrieveAllCatGr();
    }
    @PostMapping("/addCatGr")
    public  CategorieGroupe addCatGr(@RequestBody CategorieGroupe categorieGroupe){
        return catGrService.addCatGr(categorieGroupe);
    }
    @PutMapping("/updateCatGr")
    public CategorieGroupe updateCatGr(@RequestBody CategorieGroupe categorieGroupe){
        return catGrService.upadateCatGr(categorieGroupe);
    }
    @DeleteMapping("/deleteCatGr/{IdCatGr}")
    public void removeCatGr(@PathVariable ("IdCatGr") Integer IdCatGr){
        catGrService.removeCatGr(IdCatGr);
    }
}
