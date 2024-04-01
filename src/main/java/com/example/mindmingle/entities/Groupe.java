package com.example.mindmingle.entities;

import com.example.mindmingle.repositories.GroupeRepository;
import com.example.mindmingle.repositories.UserRepository;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Groupe")
public class Groupe implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGroupe")
    private int idGroupe;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "dateGr", nullable = false)
    private Date dateGr;

    public Groupe() {
    }

    public Groupe(int idGroupe, String nom, String description, Date dateGr) {
        this.idGroupe = idGroupe;
        this.nom = nom;
        this.description = description;
        this.dateGr = dateGr;
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateGr() {
        return dateGr;
    }

    public void setDateGr(Date dateGr) {
        this.dateGr = dateGr;
    }



    public String getcategorieGroupe() {
        return categorieGroupe != null ? categorieGroupe.getNomCatGroupe() : null;
    }

    public String getCreator() {
        return creator != null ? creator.getNomUser() + " " + creator.getPrenomUser() : null;
    }



    public void setCreator(User user) {
        this.creator = user;
    }
    public void setCategorieGroupe(CategorieGroupe categorieGroupe) {
        this.categorieGroupe = categorieGroupe;
    }
    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Set<String> getMembers() {
        Set<String> memberNames = new HashSet<>();
        if (members != null) {
            for (User member : members) {
                memberNames.add(member.getNomUser() + " " + member.getPrenomUser());
            }
        }
        return memberNames;
    }
    public void addMember(User user) {
        members.add(user);
    }

    public void setMessage(Set<User> members) {
        this.members = members;
    }
    public class MessageInfo {
        private String contenuMsg;
        private String owner;
        private Date createdAt;



        public MessageInfo(String contenuMsg, String owner, Date createdAt) {
            this.contenuMsg = contenuMsg;
            this.owner = owner;
            this.createdAt = createdAt;


        }

        // Add getters for the fields
        public String getContenuMsg() {
            return contenuMsg;
        }

        public String getOwner() {
            return owner;
        }


        public Date getCreatedAt() {
            return createdAt;
        }
    }

    public Set<MessageInfo> getMessages() {
        Set<MessageInfo> messageInfos = new HashSet<>();
        if (messages != null) {
            for (Message message : messages) {
                String ownerFullName = message.getUserName();
                messageInfos.add(new MessageInfo(message.getContenuMsg(), ownerFullName, message.getCreatedAt()));
            }
        }
        return messageInfos;
    }
    public void removeMessage(Message message) {
        messages.remove(message);
        message.setGroupe(null);
    }

    @ManyToOne
    private User creator;

    @ManyToOne
    private CategorieGroupe categorieGroupe;

    @OneToMany(mappedBy = "groupe",cascade = CascadeType.ALL)
    private Set<Message> messages;

    @ManyToMany(mappedBy = "groupesJoined")
    private Set<User> members;


}
