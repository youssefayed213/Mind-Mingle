package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Evenement;
import com.example.mindmingle.entities.Groupe;

import com.example.mindmingle.entities.Thematique;
import com.example.mindmingle.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement,Integer> {


    long countByThematiqueAndUser(Thematique thematicCategory, User user);
    Evenement getEvenementByIdEvent(int idEvent);


}
