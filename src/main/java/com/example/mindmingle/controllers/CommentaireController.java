package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Commentaire;
import com.example.mindmingle.entities.Post;
import com.example.mindmingle.entities.User;

import com.example.mindmingle.repositories.CommentaireRepository;
import com.example.mindmingle.repositories.PostRepository;
import com.example.mindmingle.repositories.UserRepository;
import com.example.mindmingle.services.BadWordService;
import com.example.mindmingle.services.ICommentaireService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/Comments")
public class CommentaireController {
    ICommentaireService commentaireService;
    BadWordService badWordService;
    PostRepository postRepository;
    UserRepository userRepository;
    CommentaireRepository commentaireRepository;
//    public static final String ACCOUNT_SID = "ACf785d947a6ff3386444c79acfae41e4d";
//    public static final String AUTH_TOKEN = "3d8505c3481b730e7091a032012663c9";

    @GetMapping("/retrieve-all-comments")
    public List<Commentaire> getComments(){
        List <Commentaire> listComments = (List<Commentaire>) commentaireService.retreiveAllComments();
        return listComments;
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Commentaire>> getCommentsByPostId(@PathVariable int postId) {
        List<Commentaire> commentaires = commentaireService.getCommentsByPostId(postId);

        if (!commentaires.isEmpty()) {
            return new ResponseEntity<>(commentaires, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @PostMapping("/add-Comment")
//    public ResponseEntity<?> addComment(@RequestBody Commentaire comment){
//        try {
//            if (badWordService.containsBadWord(comment.getContenu())) {
//                return ResponseEntity.badRequest().body("Votre commentaire contient des mots inappropriés. Veuillez modifier votre commentaire.");
//            }
//
//            Commentaire newComment = commentaireService.addComment(comment);
//
//            // Envoyer un SMS au propriétaire du post
//           sendSmsToPostOwner(comment);
//
//            return ResponseEntity.ok(newComment);
//        } catch (Exception e) {
//            // Gérer l'exception d'envoi de SMS
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de l'envoi du SMS.");
//        }
//    }

    @PostMapping("/posts/{postId}/users/{userId}/add-Comment")
    public ResponseEntity<?> addCommentToPost(@PathVariable int postId, @PathVariable int userId, @RequestBody Commentaire comment) {
        try {
            // Vérifier si le commentaire contient des mots inappropriés
            if (badWordService.containsBadWord(comment.getContenu())) {
                return ResponseEntity.badRequest().body("Ce commentaire enfreint les règles générales de notre plateforme et ne peut donc pas être publié.");
            }

            // Récupérer le post correspondant à l'ID
            Post post = postRepository.findByIdPost(postId);

            // Récupérer l'utilisateur correspondant à l'ID
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId));

            // Associer le post et l'utilisateur au commentaire
            comment.setPost(post);
            comment.setUser(user);

            // Ajouter le commentaire à la base de données
            Commentaire newComment = commentaireService.addComment(comment);

//        // Envoyer un SMS au propriétaire du post
//        sendSmsToPostOwner(comment);

            return ResponseEntity.ok(newComment);
        } catch (Exception e) {
            // Gérer les exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de l'ajout du commentaire.");
        }
    }

//    @PutMapping("/update-Comment")
//    public Commentaire updateComment(@RequestBody Commentaire comment) {
//
//        return commentaireService.updateComment(comment);
//    }


    //    @DeleteMapping("/delete-Comment/{idComment}")
//    public ResponseEntity<String> removeComment(@PathVariable ("idComment")Integer idComment){
//        commentaireService.removeComment(idComment);
//        String message = "Commentaire avec l'id " + idComment + " a ete supprime ";
//        return ResponseEntity.ok (message);
//
//
//    }
    @DeleteMapping("/comments/delete/{idComment}/{idUser}")
    public ResponseEntity<String> removeComment(@PathVariable("idComment") Integer idComment, @PathVariable("idUser") Integer idUser) {
        commentaireService.removeComment(idComment, idUser);
        return ResponseEntity.ok("Commentaire supprimé avec succès.");
    }



//
//
//    void sendSmsToPostOwner(Commentaire commentaire) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//        //String userPhoneNumber = commentaire.getPost().getUser().getTel();
//
//        String messageContent = "Bonjour, un nouveau commentaire a été ajouté à votre post : " + commentaire.getContenu();
//
//        // Envoyer le SMS avec le numéro de téléphone statique
//        Message message = Message.creator(
//                        new PhoneNumber("+21650734421"), // to
//                        new PhoneNumber("+19382014036"), // from
//                        messageContent)
//                .create();
//
//        System.out.println("SMS sent: " + message.getSid());
//    }

    @PutMapping("/comments/{idUser}/{idComment}")
    public ResponseEntity<String> updateComment(@PathVariable int idUser, @PathVariable int idComment, @RequestBody Commentaire updatedComment) {
        try {
            commentaireService.updateComment(idUser, idComment, updatedComment);
            return ResponseEntity.ok("Comment updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

