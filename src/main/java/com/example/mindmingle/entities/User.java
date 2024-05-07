package com.example.mindmingle.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")

public class User implements UserDetails, Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idUser;

    @NotBlank(message = "Nom is mandatory")
    @Column(name = "nomUser",nullable = false)
    private String nomUser;
    @NotBlank(message = "Prenom is mandatory")
    @Column(name = "prenomUser",nullable = false)
    private String prenomUser;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username",nullable = false, unique = true)
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password",nullable = false)
    private String password;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth should be in the past")
    @Column(name = "dateNaiss",nullable = false)
    private LocalDate dateNaiss;

    @NotBlank(message = "Telephone is mandatory")
    @Column(name = "tel",nullable = false, unique = true)
    private String tel;

    @Enumerated(EnumType.STRING)
    private RoleUser role;


    @Column(name = "numEtudiant",nullable = true,unique = true)
    private Long numEtudiant;

    @Column(name = "numEnseignant",nullable = true,unique = true)
    private Long numEnseignant;

    @Column(name = "numExpert",nullable = true,unique = true)
    private Long numExpert;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "blocked")
    private boolean blocked;

    @Column(name = "failed_login_attempts")
    private int failedLoginAttempts;

    @Lob
    @Column(name = "imageProfil", columnDefinition = "longblob")
    private byte[] imageProfil;

    @Column(name = "total_feedback_score")
    private Double totalFeedbackScore;
    @Column(name = "total_feedback_submissions")
    private Integer totalFeedbackSubmissions;

    @Column(name = "registration_date")
    private LocalDate registrationDate;


    public User(int idUser, String nomUser, String prenomUser, String email, String password, LocalDate dateNaiss, String tel, RoleUser role, Long numEtudiant, Long numEnseignant, Long numExpert) {
        this.idUser = idUser;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.email = email;
        this.password = password;
        this.dateNaiss = dateNaiss;
        this.tel = tel;
        this.role = role;
        this.numEtudiant = numEtudiant;
        this.numEnseignant = numEnseignant;
        this.numExpert = numExpert;
    }

    public User() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNomUser() {
        return nomUser;
    }



    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;

    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

/* public String getUsername() {
        return email;
    }*/

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public RoleUser getRole() {
        return role;
    }

    public void setRole(RoleUser role) {
        this.role = role;
    }

    public Long getNumEtudiant() {
        return numEtudiant;
    }

    public void setNumEtudiant(Long numEtudiant) {
        this.numEtudiant = numEtudiant;
    }

    public Long getNumEnseignant() {
        return numEnseignant;
    }

    public void setNumEnseignant(Long numEnseignant) {
        this.numEnseignant = numEnseignant;
    }

    public Long getNumExpert() {
        return numExpert;
    }


    public Set<Groupe> getGroupesJoined() {
        return groupesJoined;
    }


    @OneToMany(mappedBy = "expert",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Evenement> evenements;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Inscription> inscriptions;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy = "creator",cascade = CascadeType.ALL)
    private Set<Groupe> groupesCreated;


    public void setNumExpert(Long numExpert) {
        this.numExpert = numExpert;
    }

    public void setGroupesJoined(Set<Groupe> groupesJoined) {
        this.groupesJoined = groupesJoined;
    }



    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public boolean isBlocked() {return blocked;}

    public void setBlocked(boolean blocked) {this.blocked = blocked;}

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public void incrementFailedLoginAttempts() {
        this.failedLoginAttempts++;
    }

    public void resetFailedLoginAttempts() {
        this.failedLoginAttempts = 0;
    }

    public byte[] getImageProfil() {
        return imageProfil;
    }

    public void setImageProfil(byte[] imageProfil) {
        this.imageProfil = imageProfil;
    }

    public Double getTotalFeedbackScore() {
        return totalFeedbackScore;
    }

    public void setTotalFeedbackScore(Double totalFeedbackScore) {
        this.totalFeedbackScore = totalFeedbackScore;
    }

    public Integer getTotalFeedbackSubmissions() {
        return totalFeedbackSubmissions;
    }

    public void setTotalFeedbackSubmissions(Integer totalFeedbackSubmissions) {
        this.totalFeedbackSubmissions = totalFeedbackSubmissions;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Message> messages;




    @ManyToMany
    private Set<Groupe> groupesJoined;




    @OneToMany(mappedBy = "etudiant",cascade = {CascadeType.ALL, CascadeType.REMOVE})
    private Set<RendezVous> rendezVousEtudiant;

    @OneToMany(mappedBy = "expert",cascade = {CascadeType.ALL, CascadeType.REMOVE})

    private Set<RendezVous> rendezVousExpert;

    @OneToOne
    private ProfilEtudiant profilEtudiant;



    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL, CascadeType.REMOVE})
    private List<Token> tokens;

}