package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Evenement")
public class Evenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvent")
    private int idEvent;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "dateEvent", nullable = false)
    private Date dateEvent;

    @Column(name = "lieu", nullable = false)
    private String lieu;

    public Evenement() {
    }

    public Evenement(int idEvent, String titre, String description, Date dateEvent, String lieu) {
        this.idEvent = idEvent;
        this.titre = titre;
        this.description = description;
        this.dateEvent = dateEvent;
        this.lieu = lieu;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @ManyToOne
    private User expert;

    @OneToMany(mappedBy = "evenement",cascade = CascadeType.ALL)
    private Set<Inscription> inscriptions;
}
