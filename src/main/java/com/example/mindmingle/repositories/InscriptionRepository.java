package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription,Integer> {
}
