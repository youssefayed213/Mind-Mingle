package com.example.mindmingle.controller;

import com.example.mindmingle.entities.Inscription;
import com.example.mindmingle.service.IInscripitionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Inscripition")
public class InscriptionController {
    IInscripitionService iInscripitionService;

    @GetMapping("/retriveAllInscription")
    public List<Inscription> getAllInscriptions(){
        return iInscripitionService.retriveAllInscription();
    }
    @PostMapping("/addInscription")
    public Inscription addInscription(@RequestBody Inscription inscription){
        return iInscripitionService.addInscription(inscription);
    }
    @PutMapping("/updateInscription")
    public Inscription updateInscription(@RequestBody Inscription inscription){

        return iInscripitionService.updateInscription(inscription);
    }
    @DeleteMapping("/deleteInscription/{idInscription}")
    public void removeInscription(@PathVariable ("idInscription") int idInscription){
        iInscripitionService.removeInscription(idInscription);
    }


}
