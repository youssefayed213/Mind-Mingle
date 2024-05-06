package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Groupe")
public class Groupe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGroupe")
    private int idGroupe;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "dateGr", nullable = false)
    private Date dateGr;

    public Groupe() {
    }

    public Groupe(int idGroupe, String nom, String description, Date dateGr) {
        this.idGroupe = idGroupe;
        this.nom = nom;
        this.description = description;
        this.dateGr = dateGr;
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateGr() {
        return dateGr;
    }

    public void setDateGr(Date dateGr) {
        this.dateGr = dateGr;
    }


    @ManyToOne
    private User creator;

    @ManyToOne
    private CategorieGroupe categorieGroupe;

    @OneToMany(mappedBy = "groupe",cascade = CascadeType.ALL)
    private Set<Message> messages;

    @ManyToMany(mappedBy = "groupesJoined")
    private Set<User> members;
}
