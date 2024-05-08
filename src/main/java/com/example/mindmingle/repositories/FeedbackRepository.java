package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback , Integer> {
    @Query("SELECT f.note, COUNT(f) FROM Feedback f GROUP BY f.note")
    List<Object[]> countFeedbacksByNote();

    @Query("SELECT f FROM Feedback f WHERE f.rendezVous.idRdv = :idRdv AND f.rendezVous.etudiant.idUser = :idUser")
    Feedback findByIdUserAndIdRdv(@Param("idUser") int idUser, @Param("idRdv") int idRdv);

    void deleteByRendezVousIdRdv(Integer idRdv);

}

