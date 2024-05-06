package com.example.mindmingle.service;

import com.example.mindmingle.controller.MeetQuickstartController;
import com.example.mindmingle.entities.*;
import com.example.mindmingle.repositories.EvenementRepository;
import com.example.mindmingle.repositories.InscriptionRepository;
import com.example.mindmingle.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EvenementServiceImpl implements IEvenemntService {
    EvenementRepository evenementRepository;
    UserRepository ur;
    InscriptionRepository inscriptionRepository;



   @Override
   public Evenement addEvenement(Evenement evenement) {
       List<User> usersWithMultipleRegistrations = ur.findUsersWithMultipleRegistrationsForThematique(evenement.getThematique());
       // Perform any necessary logic with the retrieved users, such as sending notifications or processing data
       for (User user : usersWithMultipleRegistrations) {
         checkAndNotifyUserForNewEvent(evenement, user);
       //System.out.println(user.getTel());
       }
       return evenementRepository.save(evenement);
    }


    @Override
    public Evenement updateEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    @Override
    public Void removeEvenement(int idEvent) {
        evenementRepository.deleteById(idEvent);
        return null;
    }


    @Override
    public List<Evenement> retriveAllEvenement() {
        return  evenementRepository.findAll();    }

    public String getUserPhoneNumber(User user) {
        return user.getTel();
    }

    /////////////////////////
    public String generateMeetingLinkIfOnline(Evenement evenement) throws Exception {
        if (evenement.getTypeEvent().equals("EN_LIGNE")) {
            return MeetQuickstartController.main();
        }
        return null;
    }


    @Override
    public void checkAndNotifyUserForNewEvent(Evenement newEvent, User user) {
        SmsService smsService = new SmsService();

            String recipientPhoneNumber = getUserPhoneNumber(user);
            if (recipientPhoneNumber != null) {
                String messageBody = "You've exceeded 2 registrations for thematic category '"
                        + newEvent.getThematique() + "'. Please check your account.";
                try {
                    smsService.sendSms(recipientPhoneNumber, messageBody);
                   // System.out.println(recipientPhoneNumber);

                } catch (Exception e) {
                    System.out.println("Failed to send SMS: " + e.getMessage());

        }}
    }
/////////////
//@Override
//public String registerUserToEvent(Integer eventId, Integer userId) throws Exception {
//    Optional<User> optionalUser = ur.findById(userId);
//    Optional<Evenement> optionalEvent = evenementRepository.findById(eventId);
//
//    if (!optionalUser.isPresent()) {
//        return "User not found";
//    }
//
//    if (!optionalEvent.isPresent()) {
//        return "Event not found";
//    }
//    User user = optionalUser.get();
//    Evenement event = optionalEvent.get();
//
//    if(optionalEvent.get().getTypeEvent()=="Presentielle"){
//        //String emailBody = "Dear " + user.getNomUser() + ",\n\nYou have successfully registered for the event: " + event.getDescription()+ ".\n\nThank you.";
//
//        //MailService.sendEmail(user.getEmail(), "Event Registration Confirmation",emailBody);
//
//    }
//    else{
////        String emailBody = "Dear " + user.getNomUser() + ",\n\nYou have successfully registered for the event: " + event.getDescription()+ "this is the link for the meet :  " +
////        MeetQuickstartController.main() +
////                "  .\n\nThank you.";
////
////        MailService.sendEmail(user.getEmail(), "Event Registration Confirmation",emailBody);
//
//
//
//    }
//    return "regestration successfull";
//    //return inscriptionRepository.save(e);
//   }
@Override
public String registerUserToEvent(Integer eventId, Integer userId) throws Exception {
    User user = ur.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found for userId: " + userId));
    Evenement evenement = evenementRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Evenement not found for eventId: " + eventId));

    Optional<Evenement> optionalEvent = evenementRepository.findById(eventId);


    // Create Inscription object to save
    Inscription inscription = new Inscription();
    inscription.setUser(user);
    inscription.setEvenement(evenement);
    inscription.setDateInscription(new Date());

    // Set status of inscription (assuming you have a default status or enum)
    inscription.setStatutInscription(StatutInscription.pending);


    // Save Inscription
    Inscription savedInscription = inscriptionRepository.save(inscription);

    if (evenement.getTypeEvent().equals("Presentielle")) {
        // Send confirmation email for in-person event
        sendConfirmationEmail(user, evenement, "Event Registration Confirmation", "You have successfully registered for the event: " + evenement.getDescription());
    } else {
        // Send confirmation email with meet link for online event
        String meetLink = MeetQuickstartController.main(); // Assuming this retrieves the meet link
        sendConfirmationEmail(user, evenement, "Event Registration Confirmation", "You have successfully registered for the event: " + evenement.getDescription() + ". This is the meet link: " + meetLink);
    }

    return "Registration successful";
}

    private void sendConfirmationEmail(User user, Evenement event, String subject, String messageBody) {
        // Example implementation for sending email
        String emailBody = "Dear " + user.getNomUser() + ",\n\n" + messageBody + "\n\nThank you.";
        MailService.sendEmail(user.getEmail(), subject, emailBody);
    }





}






