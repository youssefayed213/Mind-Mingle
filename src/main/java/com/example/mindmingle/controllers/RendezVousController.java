package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Post;
import com.example.mindmingle.entities.RendezVous;
import com.example.mindmingle.entities.TypeRdv;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.RendezVousRepository;
import com.example.mindmingle.repositories.UserRepository;
import com.example.mindmingle.services.IRendezVous;
import com.example.mindmingle.services.RendezVousImpl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;



import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/RendezVous")

public class RendezVousController {
    IRendezVous rendezVous;
    RendezVousRepository rendezVousRepository;
    UserRepository userRepository;
    RendezVousImpl rendezVousImpl;


    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/getRendezVous")
    public List<RendezVous> getRendezVous(){
        List <RendezVous> listRendezVous = (List<RendezVous>) rendezVous.retreiveAllRendezVous();
        return listRendezVous;
    }
    @PostMapping("/users/{userId}/add-RendezVous")
    public ResponseEntity<RendezVous> addRendezVous(@PathVariable int userId, @RequestBody RendezVous rdv) {
        System.out.println("Received POST request with userId: " + userId);
        System.out.println("Received RendezVous object: " + rdv);

        try {
            // Recherche de l'utilisateur par ID
            User user = userRepository.findByIdUser(userId);

            if (user == null) {
                return ResponseEntity.notFound().build(); // Utilisateur non trouvé
            }

            // Associer l'utilisateur au rendez-vous
            rdv.setEtudiant(user);

            // Enregistrer le rendez-vous
            RendezVous savedRdv = rendezVousRepository.save(rdv);

            // Envoyer un email en fonction du type de rendez-vous
            if (savedRdv.getTypeRdv() == TypeRdv.EnPersonne) {
                sendInPersonMeetingEmail(savedRdv);
            } else if (savedRdv.getTypeRdv() == TypeRdv.EnLigne) {
                sendOnlineMeetingEmail(savedRdv);
            }

            return ResponseEntity.ok(savedRdv); // Rendez-vous ajouté avec succès
        } catch (Exception e) {
            log.error("Erreur lors de l'ajout du rendez-vous : " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur serveur
        }
    }

    private void sendInPersonMeetingEmail(RendezVous rdv) {
        String subject = "Détails de votre rendez-vous en personne";
        String body = "Votre rendez-vous est fixé en personne à l'adresse : " + rdv.getLieu();

        sendEmail(rdv.getEtudiant().getEmail(), subject, body);
    }

    private void sendOnlineMeetingEmail(RendezVous rdv) {
        String subject = "Détails de votre rendez-vous en ligne";
        String meetingLink;
        meetingLink = generateMeetingLink();

        String body = "Votre rendez-vous est fixé en ligne. Voici le lien de la réunion : " + meetingLink;

        sendEmail(rdv.getEtudiant().getEmail(), subject, body);
    }

    private void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        try {
            javaMailSender.send(message);
            log.info("Email envoyé avec succès à : " + toEmail);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email à : " + toEmail, e);
        }
    }

    private String generateMeetingLink() {
        // Ici, vous pouvez implémenter la logique pour générer le lien de réunion en ligne
        // Cela pourrait impliquer l'utilisation d'une API tierce comme Zoom

        // Par exemple, vous pouvez générer un lien fictif pour l'instant :
        String meetingLink = "https://example.com/online-meeting";

        return meetingLink;
    }

//    private String generateMeetingLink() throws IOException, GeneralSecurityException {
//        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        //JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//
//        // Charger les informations de client secrets depuis credentials.json
//        InputStream in = getClass().getResourceAsStream("/credentials.json");
//        if (in == null) {
//            log.error("Le fichier credentials.json n'a pas été trouvé dans les ressources.");
//            throw new FileNotFoundException("Le fichier credentials.json n'a pas été trouvé dans les ressources.");
//        }
//      //  GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
//
//        // Initialiser le flux d'autorisation OAuth2
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, jsonFactory, clientSecrets, Collections.singleton(CalendarScopes.CALENDAR_EVENTS))
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
//                .setAccessType("offline")
//                .build();
//
//        // URL de redirection pour l'autorisation OAuth2
//        String redirectUri = "http://localhost:8080/oauth2callback";
//        String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
//
//        // Charger les informations d'identification de l'utilisateur
//        Credential credential = flow.loadCredential("user");
//
//        // Initialiser le service Google Calendar
//        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
//                httpTransport, jsonFactory, credential)
//                .setApplicationName("Votre application")
//                .build();
//
//        // Créer un événement pour la réunion Google Meet
//        Event event = new Event()
//                .setSummary("Réunion en ligne via Google Meet")
//                .setDescription("Réunion en ligne via Google Meet");
//
//        // Définir les paramètres de la réunion
//        event.setConferenceData(new ConferenceData()
//                .setCreateRequest(new CreateConferenceRequest()
//                        .setRequestId("meetRequestId")));
//
//        // Définir l'heure de début et de fin de la réunion
//        DateTime now = new DateTime(System.currentTimeMillis());
//        event.setStart(new EventDateTime().setDateTime(now));
//        event.setEnd(new EventDateTime().setDateTime(new DateTime(now.getValue() + 3600000)));
//
//        // Insérer l'événement dans Google Calendar
//        String calendarId = "primary";
//        event = service.events().insert(calendarId, event).execute();
//        if (event == null) {
//            throw new IllegalStateException("L'événement créé est null après l'appel à l'API Google Calendar.");
//        }
//
//        // Récupérer le lien de la réunion Google Meet
//        ConferenceData conferenceData = event.getConferenceData();
//        String meetingLink = conferenceData.getEntryPoints().get(0).getUri();
//
//        return meetingLink;
//    }

    @GetMapping("/users/{userId}/rendezvous/{idRdv}")
    public ResponseEntity<RendezVous> getRendezVousByUserIdAndId(@PathVariable("userId") int idUser, @PathVariable("idRdv") int idRdv) {
        RendezVous rendezVousByUserIdAndId = rendezVous.retrieveRendezVousByUserIdAndId(idUser, idRdv); // Correction ici : Utilisez 'rendezVous' au lieu de 'rendezVousImpl'
        if (rendezVous!= null) {
            return ResponseEntity.ok(rendezVousByUserIdAndId);
        } else {
            return ResponseEntity.notFound().build(); // Retourne 404 si le rendez-vous n'a pas été trouvé
        }
    }
    @PutMapping("/update-RendezVous/{idUser}")
    public ResponseEntity<RendezVous> updateRendezVous(@PathVariable("idUser") int idUser, @RequestBody RendezVous rdv) {
        // Supposons que votre service ait une méthode updateRendezVous
        RendezVous updatedRendezVous = rendezVous.updateRendezVous(idUser, rdv);

        if (updatedRendezVous!= null) {
            return ResponseEntity.ok(updatedRendezVous);
        } else {
            return ResponseEntity.notFound().build(); // Retourne 404 si le RendezVous n'a pas été trouvé
        }
    }

    @DeleteMapping("/delete-RendezVous/{idUser}/{idRdv}")
    public ResponseEntity<String> removeRendezVous(@PathVariable("idUser") Integer idUser, @PathVariable("idRdv") Integer idRdv) {
        rendezVous.removeRendezVous(idUser, idRdv);
        String message = "Rendez-vous avec l'id " + idRdv + " pour l'utilisateur " + idUser + " a été supprimé.";
        return ResponseEntity.ok(message);
    }

//    @GetMapping("/search")
//    List<RendezVous> searchRendezVous(@RequestParam(value = "date", required = false) String dateStr,
//                                      @RequestParam(value = "type", required = false) String typeStr,
//                                      @RequestParam(value = "lieu", required = false) String lieu) {
//        List<RendezVous> allRendezVous = rendezVous.retreiveAllRendezVous();
//
//        return allRendezVous.stream()
//                .filter(rdv -> (dateStr == null || dateMatches(rdv.getDateRdv(), dateStr)) &&
//                        (typeStr == null || rdv.getTypeRdv().name().equalsIgnoreCase(typeStr)) &&
//                        (lieu == null || rdv.getLieu().equalsIgnoreCase(lieu)))
//                .collect(Collectors.toList());
//    }
//
//    private boolean dateMatches(java.util.Date date, String dateStr) {
//        System.out.println("date: " + date + ", dateStr: " + dateStr);
//
//        if (date == null || dateStr == null) {
//            return false;
//        }
//
//        // Convertir la Date en Instant
//        Instant instant = date.toInstant();
//
//        // Convertir l'Instant en LocalDate
//        LocalDate rdvLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//
//        try {
//            // Parser la chaîne de date fournie
//            LocalDate providedDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
//            System.out.println("rdvLocalDate: " + rdvLocalDate + ", providedDate: " + providedDate);
//
//            // Comparer les dates
//            return rdvLocalDate.equals(providedDate);
//        } catch (DateTimeParseException e) {
//            System.out.println("Erreur de parsing de la date : " + e.getMessage());
//            return false;
//        }
//    }





}
