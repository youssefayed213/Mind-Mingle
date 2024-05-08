package com.example.mindmingle.services;

import com.example.mindmingle.entities.Feedback;
import com.example.mindmingle.entities.NoteFeedback;
import com.example.mindmingle.entities.RendezVous;
import com.example.mindmingle.repositories.FeedbackRepository;
import com.example.mindmingle.repositories.RendezVousRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor

public class FeedbackService implements IFeedback {

     FeedbackRepository feedbackRepository;
     RendezVousRepository rendezVousRepository;



    public Feedback addFeedback(Integer idUser, Integer idRdv, Feedback feedback) {

        RendezVous rendezVous = rendezVousRepository.findById(idRdv)
                .orElseThrow(() -> new IllegalArgumentException("RendezVous not found for id: " + idRdv));
        feedback.setRendezVous(rendezVous);
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedback(Integer idUser, Integer idRdv, Feedback feedback) {
        // Récupérer le RendezVous correspondant à l'idRdv
        RendezVous rendezVous = rendezVousRepository.findById(idRdv)
                .orElseThrow(() -> new IllegalArgumentException("RendezVous not found for id: " + idRdv));

        // Récupérer le Feedback à mettre à jour
        Feedback existingFeedback = feedbackRepository.findById(feedback.getIdFeedback())
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found for id: " + feedback.getIdFeedback()));

        // Mettre à jour les propriétés du Feedback avec les nouvelles valeurs
        existingFeedback.setCommentaire(feedback.getCommentaire());
        existingFeedback.setNote(feedback.getNote());
        existingFeedback.setDateFeedback(feedback.getDateFeedback());
        existingFeedback.setRendezVous(rendezVous); // Associer le RendezVous au Feedback

        // Sauvegarder le Feedback mis à jour avec le RendezVous associé
        return feedbackRepository.save(existingFeedback);
    }

    @Override
    public void removeFeedback(Integer idUser, Integer idRdv, Integer idFeedback) {
        feedbackRepository.deleteById(idFeedback);
    }

    @Override
    public Feedback retrieveFeedback(Integer idUser, Integer idRdv, Integer idFeedback) {
        return feedbackRepository.findById(idFeedback)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + idFeedback));
    }




    @Override
    public List<Feedback> retrieveAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public Map<NoteFeedback, Long> countFeedbacksByNote() {
        List<Object[]> result = feedbackRepository.countFeedbacksByNote();
        Map<NoteFeedback, Long> feedbacksByNote = new HashMap<>();
        for (Object[] row : result) {
            NoteFeedback note = (NoteFeedback) row[0];
            Long count = (Long) row[1];
            feedbacksByNote.put(note, count);
        }
        return feedbacksByNote;
    }

    public Feedback getFeedbackByIdUserAndIdRdv(int idUser, int idRdv) {
        return feedbackRepository.findByIdUserAndIdRdv(idUser, idRdv);
    }

//    @Override
//    public Optional<Feedback> findByIdUserAndIdRdv(int idUser, int idRdv) {
//
//        Optional<RendezVous> rendezVousOpt = rendezVousRepository.findByEtudiant_Id(idUser);
//        if (!rendezVousOpt.isPresent()) {
//            return Optional.empty();
//        }
//        RendezVous rendezVous = rendezVousOpt.get();
//        return feedbackRepository.findByRendezVous_Id(rendezVous.getIdRdv());
//    }
}






