package com.example.mindmingle.services;


import com.example.mindmingle.entities.Post;
import com.example.mindmingle.entities.ProfilEtudiant;
import com.example.mindmingle.entities.RendezVous;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.FeedbackRepository;
import com.example.mindmingle.repositories.ProfilEtudiantRepository;
import com.example.mindmingle.repositories.RendezVousRepository;
import com.example.mindmingle.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RendezVousImpl implements IRendezVous{

    RendezVousRepository rendezVousRepository;
    ProfilEtudiantRepository profilEtudiantRepository;
    UserRepository userRepository;
    FeedbackRepository feedbackRepository;




    public RendezVous addRendezVous(RendezVous rendezVous) {
        return  rendezVousRepository.save(rendezVous);
    }

    @Override
    public RendezVous updateRendezVous(Integer lidUser, RendezVous rendezVous) {
        // Utilisez lidUser pour mettre à jour le rendez-vous associé à un utilisateur spécifique
        return rendezVousRepository.save(rendezVous);
    }

    @Transactional
    public void removeRendezVous(Integer idUser, Integer idRdv) {
        // Trouver le RendezVous associé à l'utilisateur spécifié
        RendezVous rendezVous = rendezVousRepository.findByEtudiantIdUserAndIdRdv(idUser, idRdv);

        if (rendezVous!= null) {
            feedbackRepository.deleteByRendezVousIdRdv(idRdv);
            rendezVousRepository.deleteById(idRdv);
        } else {
            throw new EntityNotFoundException("RendezVous non trouvé pour l'utilisateur spécifié.");
        }
    }

//    @Override
//    public RendezVous updateRendezVous(RendezVous rendezVous) {
//
//        return rendezVousRepository.save(rendezVous);
//    }
//
//    @Override
//    public void removeRendezVous(Integer idRdv) {
//        rendezVousRepository.deleteById(idRdv);
//
//    }

    @Override
    public RendezVous retrieveRendezVous(Integer idRdv) {
        return rendezVousRepository.findById( idRdv).orElse(null);
    }

    @Override
    public List<RendezVous> retreiveAllRendezVous() {
        return rendezVousRepository.findAll();
    }



    @Override
    public RendezVous retrieveRendezVousByUserIdAndId(int idUser, int idRdv) {
        RendezVous rendezVous = rendezVousRepository.findById(idRdv).orElse(null);
        if (rendezVous!= null && rendezVous.getEtudiant()!= null) {
            int userId = rendezVous.getEtudiant().getIdUser();
            // Vous pouvez maintenant utiliser userId pour d'autres opérations si nécessaire
            return rendezVous;
        }
        return null;
    }


}



