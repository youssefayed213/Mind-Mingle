package com.example.mindmingle.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="Feedback")

public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFeedback")
    private int idFeedback;

    @Column(name = "commentaire")
    private String commentaire;

    @Enumerated(EnumType.STRING)
    @Column(name = "note")
    private NoteFeedback note;

    @Column(name = "dateFeedback", nullable = false)
    private LocalDate dateFeedback;
    @JsonIgnore
    @OneToOne(mappedBy = "feedback")
    private RendezVous rendezVous;


    public Feedback() {
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public NoteFeedback getNote() {
        return note;
    }

    public void setNote(NoteFeedback note) {
        this.note = note;
    }

    public LocalDate getDateFeedback() {
        return dateFeedback;
    }

    public void setDateFeedback(LocalDate dateFeedback) {
        this.dateFeedback = dateFeedback;
    }

    public RendezVous getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(RendezVous rendezVous) {
        this.rendezVous = rendezVous;
    }
}


