package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Thematique;
import com.example.mindmingle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.inscriptions i " +
            "JOIN FETCH i.evenement e " +
            "WHERE e.thematique = :thematique " +
            "GROUP BY u.idUser " +
            "HAVING COUNT(i)>=2")
    List<User> findUsersWithMultipleRegistrationsForThematique(Thematique thematique);}
