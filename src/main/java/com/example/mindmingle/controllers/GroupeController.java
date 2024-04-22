package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.GroupeRepository;
import com.example.mindmingle.repositories.UserRepository;
import com.example.mindmingle.services.IGroupe;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

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


    private static final String WEBSOCKET_ENDPOINT = "ws://localhost:8080/websocket-endpoint";
//    @Autowired
//    private StandardWebSocketClient webSocketClient;
//
//    private static final String WEBSOCKET_ENDPOINT = "ws://localhost:8080/websocket-endpoint";
//
//    @GetMapping("/getGroupe/{IdGroupe}")
//    public ResponseEntity<Void> getGroupe(@PathVariable("IdGroupe") Integer IdGroupe) throws Exception {
//        // Create a WebSocket session by performing a handshake with the WebSocket server
//        WebSocketSession session = webSocketClient.doHandshake(new MyWebSocketHandler(), WEBSOCKET_ENDPOINT).get(); // Use specific handler
//
//        // Send group ID as a message to the WebSocket server
//        session.sendMessage(new TextMessage(IdGroupe.toString()));
//
//        // You can return a ResponseEntity with appropriate status code here (optional)
//        return ResponseEntity.ok().build();
//    }
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
        // Call the service method to retrieve group messages
        Set<Groupe.MessageInfo> messages = groupeService.retrieveGroupMessages(groupId);

        // Return the set of messages as JSON response
        return messages;
    }
    @GetMapping("/descriptions")
    public List<Object[]> getUsersWithDescriptions() {
        return groupeService.getUsersWithDescriptions();
    }

}

