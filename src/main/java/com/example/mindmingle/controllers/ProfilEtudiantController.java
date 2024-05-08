package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.ProfilEtudiant;
import com.example.mindmingle.services.IProfilEtudiantService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

@RequestMapping("/ProfilEtudiant")
public class ProfilEtudiantController {
    IProfilEtudiantService profilEtudiantService;

    @GetMapping("/retrieve-all-profil")
    public List<ProfilEtudiant> getProfil(){
        List <ProfilEtudiant> listProfil = (List<ProfilEtudiant>) profilEtudiantService.retreiveAllProfil();
        return listProfil;
    }
    @PostMapping("/add-Profil")
    public ProfilEtudiant addProfil(@RequestBody ProfilEtudiant pe){
        return profilEtudiantService.addProfil(pe);
    }

    @PutMapping("/update-Profil")
    public ProfilEtudiant updateProfil(@RequestBody ProfilEtudiant pe) {

        return profilEtudiantService.addProfil(pe);
    }
    @DeleteMapping("/delete-Profil/{idProfil}")
    public ResponseEntity<String> removeProfil(@PathVariable ("idProfil")Integer idProfil){
        profilEtudiantService.removeProfil(idProfil);
        String message = "Profil avec l'id " + idProfil + " a ete supprime ";
        return ResponseEntity.ok (message);

    }
}
