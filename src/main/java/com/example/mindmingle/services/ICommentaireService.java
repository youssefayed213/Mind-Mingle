package com.example.mindmingle.services;

import com.example.mindmingle.entities.Commentaire;

import java.util.List;

public interface ICommentaireService {
    Commentaire addComment(Commentaire comment);
   // Commentaire updateComment(Commentaire comment);
   void updateComment(int idUser, int idComment, Commentaire updatedComment);
    void removeComment(Integer idComment);
   void removeComment(Integer idComment, Integer userId);

        Commentaire retrieveComment(Integer idComment);
    List<Commentaire> retreiveAllComments();
    List<Commentaire> getCommentsByPostId(int idPost);

    }
