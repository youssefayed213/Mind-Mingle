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
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Expert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExpert;

    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    private String dossier;
    @Lob
    private byte[] dossierContent;
}
