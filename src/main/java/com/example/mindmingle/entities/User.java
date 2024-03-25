package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "User")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idUser;

    @Column(name = "NomUser",nullable = false)
    private String NomUser;

    @Column(name = "PrenomUser",nullable = false)
    private String PrenomUser;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "dateNaiss",nullable = false)
    private LocalDate dateNaiss;

    @Column(name = "tel",nullable = false)
    private String tel;

    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @Column(name = "numEtudiant",nullable = true)
    private Long numEtudiant;

    @Column(name = "numEnseignant",nullable = true)
    private Long numEnseignant;

    @Column(name = "numExpert",nullable = true)
    private Long numExpert;

    public User(int idUser, String nomUser, String prenomUser, String email, String password, LocalDate dateNaiss, String tel, RoleUser role, Long numEtudiant, Long numEnseignant, Long numExpert) {
        this.idUser = idUser;
        NomUser = nomUser;
        PrenomUser = prenomUser;
        this.email = email;
        this.password = password;
        this.dateNaiss = dateNaiss;
        this.tel = tel;
        this.role = role;
        this.numEtudiant = numEtudiant;
        this.numEnseignant = numEnseignant;
        this.numExpert = numExpert;
    }

    public User() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNomUser() {
        return NomUser;
    }

    public void setNomUser(String nomUser) {
        NomUser = nomUser;
    }

    public String getPrenomUser() {
        return PrenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        PrenomUser = prenomUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public RoleUser getRole() {
        return role;
    }

    public void setRole(RoleUser role) {
        this.role = role;
    }

    public Long getNumEtudiant() {
        return numEtudiant;
    }

    public void setNumEtudiant(Long numEtudiant) {
        this.numEtudiant = numEtudiant;
    }

    public Long getNumEnseignant() {
        return numEnseignant;
    }

    public void setNumEnseignant(Long numEnseignant) {
        this.numEnseignant = numEnseignant;
    }

    public Long getNumExpert() {
        return numExpert;
    }

    public void setNumExpert(Long numExpert) {
        this.numExpert = numExpert;
    }


    @OneToMany(mappedBy = "expert",cascade = CascadeType.ALL)
    private Set<Evenement> evenements;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Inscription> inscriptions;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy = "creator",cascade = CascadeType.ALL)
    private Set<Groupe> groupesCreated;

    @ManyToMany
    private Set<Groupe> groupesJoined;

    @OneToMany(mappedBy = "etudiant",cascade = CascadeType.ALL)
    private Set<RendezVous> rendezVousEtudiant;

    @OneToMany(mappedBy = "expert",cascade = CascadeType.ALL)
    private Set<RendezVous> rendezVousExpert;

    @OneToOne
    private ProfilEtudiant profilEtudiant;
}
