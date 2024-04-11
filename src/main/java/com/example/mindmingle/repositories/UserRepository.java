package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByDateNaiss(LocalDate date);
    List<User> findByPrenomUserAndNomUser(String firstName, String lastName);


   // Optional<User> findByEmail(String email);
   Optional<User> findByUsername(String username);
}
