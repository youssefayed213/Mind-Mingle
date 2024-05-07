package com.example.mindmingle.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "Post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPost")
    private int idPost;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "datePost", nullable = false)
    private Date datePost;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "dislikes")
    private Integer dislikes;

    @Column(name = "image")
    private String image;


    @Enumerated(EnumType.STRING)
    @Column(name = "typePost")
    private TypePost typePost;

    public Post(String titre, String description, String typePost) {
    }

    public TypePost getTypePost() {
        return typePost;
    }

    public void setTypePost(TypePost typePost) {
        this.typePost = typePost;
    }

    public Post() {
    }

    public Post(int idPost, String titre, String description, Date datePost) {
        this.idPost = idPost;
        this.titre = titre;
        this.description = description;
        this.datePost = datePost;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public Integer getLikes() {
        return likes != null ? likes.intValue() : 0;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes != null ? dislikes.intValue() : 0;
    }
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Commentaire> commentaires;

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //    @ManyToOne
//    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<UserPostReaction> reactions;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }





}
