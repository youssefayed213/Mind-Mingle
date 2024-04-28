package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.GroupeRepository;
import com.example.mindmingle.repositories.UserRepository;
import com.example.mindmingle.services.IGroupe;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/Groupe")
public class GroupeController {
    IGroupe groupeService; //injection des dépendances
    UserRepository userRepository;
    GroupeRepository groupeRepository;


    @GetMapping("/getGroupe/{IdGroupe}")
    public Groupe getGroupe(@PathVariable("IdGroupe") Integer IdGroupe) {
        return groupeService.retrieveGroupe(IdGroupe);
    }
    @GetMapping("/getAllGroupe")
    public List<Groupe> getAllGroupes() {
        return groupeService.retrieveAllGroupe();
    }
    @PostMapping("/addGroupe")
    public  Groupe addGroupe(@RequestBody Groupe groupe){
        return groupeService.addGroupe(groupe);
    }
    @PutMapping("/updateGroupe")
    public Groupe updateGroupe(@RequestBody Groupe groupe){
        return groupeService.upadateGroupe(groupe);
    }
    @DeleteMapping("/deleteGroupe/{IdGroupe}")
    public ResponseEntity<String> removeGroupe(@PathVariable ("IdGroupe") Integer IdGroupe){
        groupeService.removeGroupe(IdGroupe);
        return ResponseEntity.status(HttpStatus.OK).body("Gropue removed successfully.");
    }
    @PostMapping("/assignToCat/{idGroupe}/{idCatGroupe}")
    public ResponseEntity<String> assignGroupeToCat(@PathVariable int idGroupe, @PathVariable int idCatGroupe) {
        Groupe assignedGroupe = groupeService.assignGroupeToCat(idGroupe, idCatGroupe);
        return ResponseEntity.status(HttpStatus.OK).body("Gropue assigned to categorie successfully.");
    }

    @PostMapping("/addMember/{groupId}/{userId}")
    public ResponseEntity<Groupe> addMemberToGroupe(@PathVariable int groupId, @PathVariable int userId) {
        Groupe groupe = groupeService.addMemberToGroupe(groupId, userId);
        return ResponseEntity.ok(groupe);
    }

    // In GroupeController
    @DeleteMapping("/removeMember/{groupId}/{userId}")
    public ResponseEntity<Groupe> deleteMemberFromGroupe(@PathVariable int groupId, @PathVariable int userId) {
        Groupe groupe = groupeService.deleteMemberFromGroupe(groupId, userId);
        return ResponseEntity.ok(groupe);
    }
    @GetMapping("/{groupId}/messages")
    public Set<Groupe.MessageInfo> retrieveGroupMessages(@PathVariable("groupId") Integer groupId) {
        Set<Groupe.MessageInfo> messages = groupeService.retrieveGroupMessages(groupId);
        return messages;
    }
    @GetMapping("/descriptions")
    public List<Object[]> getUsersWithDescriptions() {
        return groupeService.getUsersWithDescriptions();
    }
    @GetMapping("/member-count/{groupId}")
    public int getMemberCount(@PathVariable int groupId) {
        return groupeService.countMembers(groupId);
    }

    @GetMapping("/message-count/{groupId}/{date}")
    public int getMessageCountOnDate(@PathVariable int groupId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return groupeService.countMessagesOnDate(groupId, date);
    }
    @GetMapping("/message-count-between-dates/{groupId}/{startDate}/{endDate}")
    public int getMessageCountBetweenDates(@PathVariable int groupId,
                                           @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                           @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return groupeService.countMessagesBetweenDates(groupId, startDate, endDate);
    }
}

