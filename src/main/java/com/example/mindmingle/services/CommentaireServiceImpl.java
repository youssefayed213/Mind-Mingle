package com.example.mindmingle.services;

import com.example.mindmingle.entities.Commentaire;
import com.example.mindmingle.entities.Post;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.CommentaireRepository;
import com.example.mindmingle.repositories.PostRepository;
import com.example.mindmingle.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentaireServiceImpl implements ICommentaireService{
    CommentaireRepository commentaireRepository;
    PostRepository postRepository;
    UserRepository userRepository;
    @Override
    @Transactional
    public Commentaire addComment(Commentaire comment) {

        // Obtenir la date et l'heure actuelles dans le fuseau horaire local
        LocalDateTime localDateTime = LocalDateTime.now();

        // Convertir LocalDateTime en Date avec le fuseau horaire local
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        // Définir la date du post
        comment.setDateComment(date);

        return commentaireRepository.save(comment);
    }


//    @Override
//    public Commentaire updateComment(Commentaire updatedComment) {
//        Commentaire existingComment = commentaireRepository.findById(updatedComment.getIdComment()).orElse(null);
//        if (existingComment != null) {
//            existingComment.setContenu(updatedComment.getContenu());
//
//            return commentaireRepository.save(existingComment);
//        }
//        return null; // Retourner null si le commentaire n'existe pas
//    }

    @Override
    public void updateComment(int idUser, int idComment, Commentaire updatedComment) {
        // Vérifier si l'utilisateur actuel est le propriétaire du commentaire
        Optional<Commentaire> existingCommentOptional = commentaireRepository.findByIdAndUserId(idComment, idUser);

        if (existingCommentOptional.isPresent()) {
            // Mettre à jour le commentaire
            Commentaire existingComment = existingCommentOptional.get();
            existingComment.setContenu(updatedComment.getContenu());
            existingComment.setDateComment(new Date()); // Mettre à jour la date de modification du commentaire

            commentaireRepository.save(existingComment);
        } else {
            throw new RuntimeException("User is not the owner of the comment or comment not found.");
        }
    }


    @Override
    public void removeComment(Integer idComment) {
        commentaireRepository.deleteById(idComment);
    }

    @Override
    public Commentaire retrieveComment(Integer idComment) {
        return commentaireRepository.findById( idComment).orElse(null);
    }


    @Override
    public List<Commentaire> retreiveAllComments() {
        return commentaireRepository.findAll();
    }

    @Override
    public List<Commentaire> getCommentsByPostId(int idPost) {
        // Récupérer le post correspondant à l'identifiant idPost
        Post post = postRepository.findById(idPost).orElse(null);

        if (post != null) {
            // Si le post existe, récupérer les commentaires associés
            return commentaireRepository.findByPost(post);
        } else {
            // Si le post n'existe pas, retourner une liste vide ou gérer l'erreur selon votre logique métier
            return Collections.emptyList();
        }
    }



    @Override
    public void removeComment(Integer idComment, Integer idUser) {
        // Récupérer l'utilisateur correspondant à l'ID de l'utilisateur
        Optional<User> optionalUser = userRepository.findById(idUser);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Récupérer le commentaire par son ID
            Optional<Commentaire> optionalCommentaire = commentaireRepository.findById(idComment);

            // Vérifier si le commentaire existe
            if (optionalCommentaire.isPresent()) {
                Commentaire commentaire = optionalCommentaire.get();

                // Vérifier si l'utilisateur actuel est le propriétaire du commentaire
                if (commentaire.getUser().equals(user)) {
                    commentaireRepository.deleteById(idComment);
                    System.out.println("Commentaire supprimé avec succès.");
                } else {
                    System.out.println("Vous n'êtes pas autorisé à supprimer ce commentaire.");
                }
            } else {
                System.out.println("Le commentaire spécifié n'existe pas.");
            }
        } else {
            System.out.println("L'utilisateur spécifié n'existe pas.");
        }
    }



    }
