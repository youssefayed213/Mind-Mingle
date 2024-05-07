package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ProfilEtudiant")
public class ProfilEtudiant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfil")
    private int idProfil;

    @Column(name = "classe", nullable = false)
    private String classe;

    @Column(name = "poids", nullable = false)
    private double poids;

    @Column(name = "taille", nullable = false)
    private double taille;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "dossier")
    private String dossier;

    public ProfilEtudiant(int idProfil, String classe, double poids, double taille, String description, String dossier) {
        this.idProfil = idProfil;
        this.classe = classe;
        this.poids = poids;
        this.taille = taille;
        this.description = description;
        this.dossier = dossier;
    }

    public ProfilEtudiant() {
    }

    public int getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(int idProfil) {
        this.idProfil = idProfil;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDossier() {
        return dossier;
    }

    public void setDossier(String dossier) {
        this.dossier = dossier;
    }

    @ManyToOne
    private CategorieProfil categorieProfil;

    @OneToOne
    private User user;

}
