package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.CategoryEtudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryEtudiantRepository extends JpaRepository<CategoryEtudiant,Integer> {
}

