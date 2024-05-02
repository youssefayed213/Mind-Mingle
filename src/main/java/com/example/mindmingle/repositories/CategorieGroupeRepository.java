package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.entities.CategorieProfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieGroupeRepository extends JpaRepository<CategorieGroupe,Integer> {
}
