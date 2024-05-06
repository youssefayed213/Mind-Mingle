package com.example.mindmingle.service;

import com.example.mindmingle.entities.*;
import com.example.mindmingle.repositories.EvenementRepository;
import com.example.mindmingle.repositories.InscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class InscriptionServiceImpl implements IInscripitionService {
    InscriptionRepository inscriptionRepository;
    EvenementRepository evenementRepository;
    EvenementServiceImpl evenementService;



    @Override
    public Inscription addInscription(Inscription inscription) {

        return inscriptionRepository.save(inscription);
    }

    @Override
    public Inscription updateInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public Void removeInscription(int idInscription) {
        inscriptionRepository.deleteById(idInscription);
        return null;
    }

    @Override
    public Inscription retriveInscription(int idInscription) {
        return inscriptionRepository.findById(idInscription).get();
    }

    @Override
    public List<Inscription> retriveAllInscription() {
        return inscriptionRepository.findAll();

    }
    private void sendNotificationEmail(Inscription inscription) {
        Evenement event = evenementRepository.getEvenementByIdEvent(inscription.getEvenement().getIdEvent());
        String recipientEmail = inscription.getUser().getEmail();
        String subject;
        String body;

        if (event.getTypeEvenement() == TypeEvenement.EN_LIGNE) {
            // Online event: Send email with meeting link
            subject = "Your Online Event Details";
            body = "Here's the meeting link: " + event.getMeetingLink();
        } else {
            // In-person event: Send email with event information
            subject = "Your In-Person Event Details";
            body = "Event details: " + event.getDescription();
        }

        MailService.sendEmail(recipientEmail, subject, body);
    }


}







