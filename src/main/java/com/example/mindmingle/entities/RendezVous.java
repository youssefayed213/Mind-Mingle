package com.example.mindmingle.entities;

import jakarta.persistence.*;
import org.hibernate.engine.internal.Cascade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "RendezVous")
public class RendezVous implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRdv")
    private int idRdv;


    @Column(name = "dateRdv",nullable = false)
    private Date dateRdv;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeRdv", nullable = false)
    private TypeRdv typeRdv;

    @Column(name = "lieu")
    private String lieu;



    public RendezVous() {
    }

    public RendezVous(int idRdv, Date dateRdv, TypeRdv typeRdv, String lieu) {
        this.idRdv = idRdv;
        this.dateRdv = dateRdv;
        this.typeRdv = typeRdv;
        this.lieu = lieu;

    }

    public int getIdRdv() {
        return idRdv;
    }

    public void setIdRdv(int idRdv) {
        this.idRdv = idRdv;
    }

    public Date getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(Date dateRdv) {
        this.dateRdv = dateRdv;
    }

    public TypeRdv getTypeRdv() {
        return typeRdv;
    }

    public void setTypeRdv(TypeRdv typeRdv) {
        this.typeRdv = typeRdv;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    public User getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(User etudiant) {
        this.etudiant = etudiant;
    }

    public User getExpert() {
        return expert;
    }

    public void setExpert(User expert) {
        this.expert = expert;
    }
    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public int getUserIdFromEtudiant() {
        return etudiant!= null? etudiant.getIdUser() : -1; // Retourne -1 ou une valeur par défaut si etudiant est null
    }

    public int getUserIdFromExpert() {
        return expert!= null? expert.getIdUser() : -1; // Retourne -1 ou une valeur par défaut si expert est null
    }
    @ManyToOne
    @JoinColumn(name = "etudiant_id", referencedColumnName = "idUser") // Remplacez 'etudiant_id' par le nom de la colonne dans la table RendezVous faisant référence à l'ID de l'étudiant
    public User etudiant;

    @ManyToOne
    @JoinColumn(name = "expert_id", referencedColumnName = "idUser")
    public User expert;



    @OneToOne (cascade = CascadeType.REMOVE)
    private Feedback feedback;

    @Override
    public String toString() {
        return String.valueOf(idRdv);
    }
}
