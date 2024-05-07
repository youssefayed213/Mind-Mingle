package com.example.mindmingle.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Evenement")


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Evenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvent")
    private int idEvent;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "dateEvent", nullable = false)
    private Date dateEvent;


    @Enumerated(EnumType.STRING)
    private Thematique thematique;


    @Column(name = "lieu", nullable = false)
    private String lieu;

    @Enumerated(EnumType.STRING)
    private TypeEvenement typeEvenement;

    //@JsonManagedReference
    @ManyToOne
    private User expert;

    @JsonManagedReference
    @OneToMany(mappedBy = "evenement",cascade = CascadeType.ALL)
    private Set<Inscription> inscriptions;


    @JsonBackReference
    @Getter
    @ManyToOne
    private  User user;
    // In Evenement.java

    public String getTypeEvent() {
        return this.typeEvenement.toString();
    }
    @Column(name = "meetingLink")
    private String meetingLink;
}
