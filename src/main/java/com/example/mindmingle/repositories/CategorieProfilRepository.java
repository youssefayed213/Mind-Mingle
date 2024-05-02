package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.CategorieProfil;
import com.example.mindmingle.entities.ProfilEtudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieProfilRepository extends JpaRepository<CategorieProfil,Integer> {
}
