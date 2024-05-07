package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "CategorieGroupe")
public class CategorieGroupe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCatGroupe")
    private int idCatGroupe;

    @Column(name = "nomCatGroupe", nullable = false)
    private String nomCatGroupe;

    public CategorieGroupe() {
    }

    public CategorieGroupe(int idCatGroupe, String nomCatGroupe) {
        this.idCatGroupe = idCatGroupe;
        this.nomCatGroupe = nomCatGroupe;
    }

    public int getIdCatGroupe() {
        return idCatGroupe;
    }

    public void setIdCatGroupe(int idCatGroupe) {
        this.idCatGroupe = idCatGroupe;
    }

    public String getNomCatGroupe() {
        return nomCatGroupe;
    }

    public void setNomCatGroupe(String nomCatGroupe) {
        this.nomCatGroupe = nomCatGroupe;
    }

    @OneToMany(mappedBy = "categorieGroupe",cascade = CascadeType.ALL)
    private Set<Groupe> groupes;
}
