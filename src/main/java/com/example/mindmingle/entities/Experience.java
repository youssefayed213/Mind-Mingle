package com.example.mindmingle.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExperience;

    private Date startDate;
    private Date endDate;
    private String experiencePlace;
    private String experienceTitle;
    private String experienceDescription;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    // Constructors, getters, and setters
}
