package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.RendezVous;
import com.example.mindmingle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous,Integer> {
}
