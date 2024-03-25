package com.example.mindmingle.entities;

import jakarta.persistence.*;

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

    @ManyToOne
    private User etudiant;

    @ManyToOne
    private User expert;
}
