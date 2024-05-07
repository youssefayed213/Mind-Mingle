package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.ProfilEtudiant;


import com.example.mindmingle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe,Integer> {
    Groupe findByIdGroupe(Integer IdGroupe);
    @Query("SELECT DISTINCT g FROM Groupe g JOIN FETCH g.members WHERE g.idGroupe = :groupId")
    List<Groupe> findGroupsWithMembersById(Integer groupId);

}
