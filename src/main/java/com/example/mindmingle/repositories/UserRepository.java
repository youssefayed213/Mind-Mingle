package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByIdUser(Integer UserId);
    @Query("SELECT u.idUser, p.description FROM User u JOIN u.profilEtudiant p")
    List<Object[]> getUsersWithDescriptions();


}
