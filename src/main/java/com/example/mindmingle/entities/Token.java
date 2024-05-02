package com.example.mindmingle.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Token")
public class Token {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idToken")
    private Integer idToken;

    @Column(name = "token")
    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedout;

    public Integer getIdToken() {
        return idToken;
    }

    public void setIdToken(Integer idToken) {
        this.idToken = idToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoggedout() {
        return loggedout;
    }

    public void setLoggedout(boolean loggedout) {
        this.loggedout = loggedout;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;




}
