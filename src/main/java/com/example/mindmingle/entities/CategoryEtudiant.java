package com.example.mindmingle.entities;




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
public class CategoryEtudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategorie;

    private String nomCategory;

    @OneToMany(mappedBy = "categoryEtudiant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Etudiant> etudiants;


    @OneToMany(mappedBy = "categoryEtudiant", cascade = CascadeType.ALL)
    private List<Objective> objectives;
}
