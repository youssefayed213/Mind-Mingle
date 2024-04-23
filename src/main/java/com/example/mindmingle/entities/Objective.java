package com.example.mindmingle.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Objective {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idObjective;

    private String objectiveTitle;
    private String objectiveDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "objective", cascade = CascadeType.ALL)
    private List<Mission> missions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_etudiant_id")
    private CategoryEtudiant categoryEtudiant;

    private int goal;
}
