package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Commentaire;
import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire,Integer> {
    List<Commentaire> findByPost(Post post);
    @Query("SELECT c FROM Commentaire c JOIN c.user u WHERE c.idComment = :idComment AND u.idUser = :idUser")
    Optional<Commentaire> findByIdAndUserId(@Param("idComment") Integer idComment, @Param("idUser") Integer idUser);

}
