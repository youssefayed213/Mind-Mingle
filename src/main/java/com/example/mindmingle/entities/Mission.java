package com.example.mindmingle.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMission;

    private String titreMission;
    private String descriptionMission;

    @Enumerated(EnumType.STRING)
    private Difficulte difficulte;
    private int valeurMission;
    @JsonIgnoreProperties("missions")
    @ManyToOne
    @JoinColumn(name = "objective_id")
    private Objective objective;

}

