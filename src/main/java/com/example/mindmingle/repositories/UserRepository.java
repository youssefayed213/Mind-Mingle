package com.example.mindmingle.repositories;


import com.example.mindmingle.entities.Thematique;

import com.example.mindmingle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByDateNaiss(LocalDate date);
    User findByIdUser(Integer UserId);
    @Query("SELECT u.idUser, p.description FROM User u JOIN u.profilEtudiant p")
    List<Object[]> getUsersWithDescriptions();
    List<User> findByPrenomUserAndNomUser(String firstName, String lastName);

    List<User> findByPrenomUser(String firstName);

    List<User> findByNomUser(String lastName);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByConfirmationToken(String token);

    int countByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.inscriptions i " +
            "JOIN FETCH i.evenement e " +
            "WHERE e.thematique = :thematique " +
            "GROUP BY u.idUser " +
            "HAVING COUNT(i)>=2")
    List<User> findUsersWithMultipleRegistrationsForThematique(Thematique thematique);





}


