package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.RendezVous;
import com.example.mindmingle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous,Integer> {
    RendezVous save(RendezVous rendezVous);
    @Query("SELECT r FROM RendezVous r JOIN r.etudiant u WHERE r.idRdv = :idRdv AND u.idUser = :userId")
    RendezVous findRendezVousByIdRdvAndUserIdFromEtudiant(@Param("idRdv") int idRdv, @Param("userId") int userId);

    RendezVous findByEtudiantIdUserAndIdRdv(Integer etudiantId, Integer idRdv);

}
