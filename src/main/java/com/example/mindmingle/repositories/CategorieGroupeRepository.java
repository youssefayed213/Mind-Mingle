package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.entities.CategorieProfil;
import com.example.mindmingle.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieGroupeRepository extends JpaRepository<CategorieGroupe,Integer> {
    CategorieGroupe findByIdCatGroupe(Integer idCatGroupe);

}
