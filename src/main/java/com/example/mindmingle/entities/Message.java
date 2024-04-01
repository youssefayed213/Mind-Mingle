package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Message")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMsg")
    private int idMsg;

    @Column(name = "contenuMsg", nullable = false)
    private String contenuMsg;

    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    public Message() {
    }

    public Message(int idMsg, String contenuMsg, Date createdAt) {
        this.idMsg = idMsg;
        this.contenuMsg = contenuMsg;
        this.createdAt = createdAt;
    }

    public int getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
    }

    public String getContenuMsg() {
        return contenuMsg;
    }

    public void setContenuMsg(String contenuMsg) {
        this.contenuMsg = contenuMsg;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Getters that return only specific fields
    public String getGroupName() {
        return groupe.getNom();  // Assuming 'nom' is the field for group name
    }

    public String getUserName() {
        return user.getNomUser() + " " + user.getPrenomUser();
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @ManyToOne
    private Groupe groupe;

    @ManyToOne
    private User user;
}
