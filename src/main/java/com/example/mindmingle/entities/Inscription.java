package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Inscription")
public class Inscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInscription")
    private int idInscription;

    @Column(name = "dateInscription", nullable = false)
    private Date dateInscription;

    @Enumerated(EnumType.STRING)
    private StatutInscription statutInscription;

    public Inscription() {
    }

    public Inscription(int idInscription, Date dateInscription, StatutInscription statutInscription) {
        this.idInscription = idInscription;
        this.dateInscription = dateInscription;
        this.statutInscription = statutInscription;
    }

    public int getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(int idInscription) {
        this.idInscription = idInscription;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public StatutInscription getStatutInscription() {
        return statutInscription;
    }

    public void setStatutInscription(StatutInscription statutInscription) {
        this.statutInscription = statutInscription;
    }

    @ManyToOne
    private User user;

    @ManyToOne
    private Evenement evenement;

}
