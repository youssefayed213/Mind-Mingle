package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.ProfilEtudiant;
import com.example.mindmingle.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilEtudiantRepository extends JpaRepository<ProfilEtudiant,Integer> {
}
