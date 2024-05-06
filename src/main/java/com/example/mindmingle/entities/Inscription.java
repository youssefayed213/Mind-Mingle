package com.example.mindmingle.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Inscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInscription")
    private int idInscription;

    @Column(name = "dateInscription", nullable = false)
    private Date dateInscription;

    @Enumerated(EnumType.STRING)
    private StatutInscription statutInscription;



    @ManyToOne
    private User user;
    @JsonBackReference
    @ManyToOne
    private Evenement evenement;



}
