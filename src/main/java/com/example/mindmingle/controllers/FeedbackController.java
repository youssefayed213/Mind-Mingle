package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Feedback;
import com.example.mindmingle.entities.NoteFeedback;
import com.example.mindmingle.entities.RendezVous;
import com.example.mindmingle.services.IFeedback;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/feedback")
public class FeedbackController {

    IFeedback feedbackService;

    @PostMapping("/{userId}/{rdvId}/add-feedback")
    public ResponseEntity<Feedback> addFeedback(@PathVariable Integer userId,
                                                @PathVariable Integer rdvId,
                                                @RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackService.addFeedback(userId, rdvId, feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    @PutMapping("/{userId}/{rdvId}/update-feedback/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Integer userId,
                                                   @PathVariable Integer rdvId,
                                                   @PathVariable Integer feedbackId,
                                                   @RequestBody Feedback feedback) {
        // Vérifier si le Feedback à mettre à jour existe dans la base de données
        Feedback existingFeedback = feedbackService.retrieveFeedback(userId, rdvId, feedbackId);
        if (existingFeedback == null) {
            return ResponseEntity.notFound().build(); // Retourner une réponse 404 si le Feedback n'existe pas
        }

        // Assurez-vous que l'id du RendezVous correspond à celui fourni dans le chemin de l'URL
        if (existingFeedback.getRendezVous() == null || existingFeedback.getRendezVous().getIdRdv() != rdvId) {
            return ResponseEntity.badRequest().build(); // Retourner une réponse 400 si l'id du RendezVous ne correspond pas
        }

        // Mettre à jour les propriétés du Feedback avec les nouvelles valeurs
        existingFeedback.setCommentaire(feedback.getCommentaire());
        existingFeedback.setNote(feedback.getNote());
        existingFeedback.setDateFeedback(feedback.getDateFeedback());

        // Sauvegarder le Feedback mis à jour
        Feedback updatedFeedback = feedbackService.updateFeedback(userId, rdvId, existingFeedback);
        return ResponseEntity.ok(updatedFeedback);
    }




    @DeleteMapping("/{userId}/{rdvId}/delete-feedback/{idFeedback}")
    public ResponseEntity<String> removeFeedback(@PathVariable Integer userId,
                                                 @PathVariable Integer rdvId,
                                                 @PathVariable Integer idFeedback) {
        feedbackService.removeFeedback(userId, rdvId, idFeedback);
        return ResponseEntity.ok("Feedback with id " + idFeedback + " deleted successfully");
    }

    @GetMapping("/{userId}/{rdvId}/get-feedback/{idFeedback}")
    public ResponseEntity<Feedback> retrieveFeedback(@PathVariable Integer userId,
                                                     @PathVariable Integer rdvId,
                                                     @PathVariable Integer idFeedback) {
        Feedback feedback = feedbackService.retrieveFeedback(userId, rdvId, idFeedback);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/feedback/{idUser}/{idRdv}")
    public ResponseEntity<Feedback> getFeedbackByUserIdAndRdvId(@PathVariable int idUser, @PathVariable int idRdv) {
        Feedback feedback = feedbackService.getFeedbackByIdUserAndIdRdv(idUser, idRdv);
        if (feedback == null) {
            // Renvoie une réponse avec un statut 404 Not Found ou un message d'erreur personnalisé
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }


    @GetMapping("/all-feedback")
    public ResponseEntity<List<Feedback>> retrieveAllFeedback() {
        List<Feedback> feedbacks = feedbackService.retrieveAllFeedback();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<NoteFeedback, Double>> getFeedbackStatsByNote() {
        Map<NoteFeedback, Long> feedbackCounts = feedbackService.countFeedbacksByNote();
        long totalFeedbacks = feedbackCounts.values().stream().mapToLong(Long::valueOf).sum();

        Map<NoteFeedback, Double> feedbackStatsByNote = new HashMap<>();
        feedbackCounts.forEach((note, count) -> {
            double percentage = (double) count / totalFeedbacks * 100.0;
            feedbackStatsByNote.put(note, percentage);
        });

        return ResponseEntity.ok(feedbackStatsByNote);
    }

}