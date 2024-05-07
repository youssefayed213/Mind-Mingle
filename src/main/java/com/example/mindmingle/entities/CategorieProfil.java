package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "CategorieProfil")
public class CategorieProfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCatProfil")
    private int idCatProfil;

    @Column(name = "nomCatProfil", nullable = false)
    private String nomCatProfil;

    public CategorieProfil() {
    }

    public CategorieProfil(int idCatProfil, String nomCatProfil) {
        this.idCatProfil = idCatProfil;
        this.nomCatProfil = nomCatProfil;
    }

    public int getIdCatProfil() {
        return idCatProfil;
    }

    public void setIdCatProfil(int idCatProfil) {
        this.idCatProfil = idCatProfil;
    }

    public String getNomCatProfil() {
        return nomCatProfil;
    }

    public void setNomCatProfil(String nomCatProfil) {
        this.nomCatProfil = nomCatProfil;
    }

    @OneToMany(mappedBy = "categorieProfil",cascade = CascadeType.ALL)
    private Set<ProfilEtudiant> profils;
}
