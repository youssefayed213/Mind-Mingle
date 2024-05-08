package com.example.mindmingle.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClasse;

    private int capacite;
    private String nomClasse;
    private Integer niveauClasse;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Etudiant> etudiants;
    @ManyToMany(mappedBy = "classeAEnseigner")
    private List<Enseignant> enseignants;
}
