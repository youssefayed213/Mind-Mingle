package com.example.mindmingle.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Commentaire")
public class Commentaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComment")
    private int idComment;

    @Column(name = "contenu", nullable = false)
    private String contenu;

    @Column(name = "dateComment", nullable = false)
    private Date dateComment;

    public Commentaire() {
    }

    public Commentaire(int idComment, String contenu, Date dateComment) {
        this.idComment = idComment;
        this.contenu = contenu;
        this.dateComment = dateComment;

    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    @ManyToOne
    @JsonIgnore
    private Post post;


    @ManyToOne
    private User user; // Ajoutez cette ligne pour la relation avec l'utilisateur


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
