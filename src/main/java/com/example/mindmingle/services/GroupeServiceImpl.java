package com.example.mindmingle.services;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.CategorieGroupeRepository;
import com.example.mindmingle.repositories.GroupeRepository;
import com.example.mindmingle.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class GroupeServiceImpl implements IGroupe{
    GroupeRepository groupeRepository;
    CategorieGroupeRepository categorieGroupeRepository;
    UserRepository userRepository;

    @Override
    public Groupe addGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public Groupe upadateGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public void removeGroupe(Integer idGroupe) {
        groupeRepository.deleteById(idGroupe);
    }

    @Override
    public Groupe retrieveGroupe(Integer idGroupe) {
        return groupeRepository.findById(idGroupe).get();
    }

    @Override
    public List<Groupe> retrieveAllGroupe() {
         return groupeRepository.findAll();
    }

    @Override
    public Groupe assignGroupeToCat(int idGroupe, int idCatGroupe) {
        Groupe groupe = groupeRepository.findByIdGroupe(idGroupe);
        CategorieGroupe categorieGroupe = categorieGroupeRepository.findByIdCatGroupe(idCatGroupe);

        // Assign the category to the group
        groupe.setCategorieGroupe(categorieGroupe);

        return groupeRepository.save(groupe);
    }

    @Override
    public Groupe addMemberToGroupe(int groupId, int userId) {
        Groupe groupe = groupeRepository.findById(groupId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        // Add user to the group using the addMember method
        groupe.addMember(user);  // Call the addMember method

        // Add the group to the user's groupesJoined (optional)
        user.getGroupesJoined().add(groupe);

        // Save the updated group to persist the changes to the database
        // Note: Saving only the group should suffice, as JPA will manage the inverse side of the relationship
        return groupeRepository.save(groupe);
    }
    public Groupe deleteMemberFromGroupe(int groupId, int userId) {
        Groupe groupe = groupeRepository.findById(groupId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        // Remove user from the group
        groupe.getMembers().remove(user);

        // Remove the group from the user's groupesJoined (optional)
        user.getGroupesJoined().remove(groupe);

        // Save the updated group to persist the changes to the database
        return groupeRepository.save(groupe);
    }


}
