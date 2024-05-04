package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Commentaire;
import com.example.mindmingle.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire,Integer> {
}
