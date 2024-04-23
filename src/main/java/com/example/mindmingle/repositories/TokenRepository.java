package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Token;
import com.example.mindmingle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    @Query("""
        Select t from Token t inner join User u
        on t.user.idUser = u.idUser
        where t.user.idUser = :userId and t.loggedout = false 
""")
    List<Token> findAllTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
