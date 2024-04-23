package com.example.mindmingle.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEtudiant;

    private float taille;
    private float point;
    private String description;
    private String dossier;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    @JsonBackReference
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    @JsonBackReference
    private CategoryEtudiant categoryEtudiant;
}

