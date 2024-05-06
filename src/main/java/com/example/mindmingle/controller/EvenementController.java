package com.example.mindmingle.controller;

import com.example.mindmingle.entities.Evenement;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.service.IEvenemntService;
import com.example.mindmingle.service.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/evenement")
@CrossOrigin(origins ="*")
public class EvenementController {
    IEvenemntService evenemntService;

    @GetMapping("/retriveAllEvenement")
    public List<Evenement> getAllEvenements(){
        return evenemntService.retriveAllEvenement();
    }
    @PostMapping("/addevenement")
    public Evenement addEvenement(@RequestBody Evenement evenement )  {
        return evenemntService.addEvenement(evenement);
    }
    @PutMapping("/updateEvenement")
    public Evenement updateEvenement(@RequestBody Evenement evenement){

        return evenemntService.updateEvenement(evenement);
    }
    @DeleteMapping("/deleteEvenement/{idEvent}")
    public void removeEvenement(@PathVariable ("idEvent") int idEvent){
        evenemntService.removeEvenement(idEvent);
    }




    @PostMapping("/register_event/event/{eventId}/user/{userId}")
    public ResponseEntity<String> registerUserToEvent(@PathVariable("eventId") Integer eventId, @PathVariable("userId") Integer userId) throws Exception{
        String registrationResult = evenemntService.registerUserToEvent(eventId, userId);

        if (registrationResult != null) {
            return ResponseEntity.ok(registrationResult);
        } else {
            return ResponseEntity.badRequest().body("Failed to register user to the event. Invalid event ID or user ID.");
    }

    }


}
