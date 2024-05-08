package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective,Integer> {
    Objective findByIdObjective(Integer idObjective);
    List<Objective> findByCategoryEtudiant_IdCategorie(Integer idCategoryEtudiant);
}
