package com.example.mindmingle.services;

import com.example.mindmingle.entities.Feedback;
import com.example.mindmingle.entities.NoteFeedback;
import com.example.mindmingle.entities.RendezVous;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IFeedback {
    Feedback addFeedback(Integer idUser, Integer idRdv, Feedback feedback);
    Feedback updateFeedback(Integer idUser, Integer idRdv, Feedback feedback);
    void removeFeedback(Integer idUser, Integer idRdv, Integer idFeedback);
    Feedback retrieveFeedback(Integer idUser, Integer idRdv, Integer idFeedback);
    List<Feedback> retrieveAllFeedback();
    Map<NoteFeedback, Long> countFeedbacksByNote();
    Feedback getFeedbackByIdUserAndIdRdv(int idUser, int idRdv);
}
