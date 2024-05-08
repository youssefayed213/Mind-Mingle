package com.example.mindmingle.services;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.CategorieGroupeRepository;
import com.example.mindmingle.repositories.GroupeRepository;
import com.example.mindmingle.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class GroupeServiceImpl implements IGroupe{



    private final GroupeRepository groupeRepository;
    private final UserRepository userRepository;
    private final CategorieGroupeRepository categorieGroupeRepository;
    private Map<String, Integer> groupIds;
    @Autowired
    public GroupeServiceImpl(GroupeRepository groupeRepository, UserRepository userRepository, CategorieGroupeRepository categorieGroupeRepository) {
        this.groupeRepository = groupeRepository;
        this.userRepository = userRepository;
        this.categorieGroupeRepository = categorieGroupeRepository;

        initializeGroupIds();
    }

    private void initializeGroupIds() {
        groupIds = new HashMap<>();
        List<Groupe> groups = groupeRepository.findAll();
        for (Groupe group : groups) {
            groupIds.put(group.getNom(), group.getIdGroupe());
        }
        updateGroupIds();

    }
    // Method to update groupIds map
    private void updateGroupIds() {
        List<Groupe> groups = groupeRepository.findAll();
        groupIds.clear();
        for (Groupe group : groups) {
            groupIds.put(group.getNom(), group.getIdGroupe());
        }
    }
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void assignUsersToGroups() {
        List<Object[]> usersWithDescriptions = getUsersWithDescriptions();
        updateGroupIds();
        for (Object[] userWithDescription : usersWithDescriptions) {
            int userId = (int) userWithDescription[0];
            String description = (String) userWithDescription[1];

            for (Map.Entry<String, Integer> entry : groupIds.entrySet()) {
                String groupName = entry.getKey();
                int groupId = entry.getValue();

                // Check if the description contains a keyword related to the group
                if (description != null && isDescriptionRelatedToGroup(description, groupName)) {
                    // Add user to the corresponding group
                    addMemberToGroupe(groupId, userId);
                }
            }
        }
    }

    // Method to check if the description contains a keyword related to the group
    private boolean isDescriptionRelatedToGroup(String description, String groupName) {
        return description.toLowerCase().contains(groupName.toLowerCase());
    }

    public List<Object[]> getUsersWithDescriptions() {
        return userRepository.getUsersWithDescriptions();
    }

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
    public Set<Groupe.MessageInfo> retrieveGroupMessages(Integer groupId) {
        // Retrieve the groupe entity by groupId
        Optional<Groupe> optionalGroupe = groupeRepository.findById(groupId);

        if (optionalGroupe.isPresent()) {
            Groupe groupe = optionalGroupe.get();
            return groupe.getMessages(); // Assuming messages are stored within the Groupe entity
        } else {
            throw new RuntimeException("Groupe not found with id: " + groupId);
        }
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

        groupe.addMember(user);

        user.getGroupesJoined().add(groupe);

        return groupeRepository.save(groupe);
    }
    public Groupe deleteMemberFromGroupe(int groupId, int userId) {
        Groupe groupe = groupeRepository.findById(groupId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        groupe.getMembers().remove(user);

        user.getGroupesJoined().remove(groupe);

        return groupeRepository.save(groupe);
    }

    public int countMembers(int groupId) {
        Groupe groupe = groupeRepository.findById(groupId).orElse(null);
        if (groupe != null && groupe.getMembers() != null) {
            return groupe.getMembers().size();
        }
        return 0;
    }
    public int countMessagesOnDate(int groupId, LocalDate date) {
        Groupe groupe = groupeRepository.findById(groupId).orElse(null);
        if (groupe != null && groupe.getMessages() != null) {
            return (int) groupe.getMessages().stream()
                    .filter(messageInfo -> {
                        LocalDateTime messageDateTime = messageInfo.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        LocalDate messageDate = messageDateTime.toLocalDate();
                        return messageDate.equals(date);
                    })
                    .count();
        }
        return 0;
    }
    public int countMessagesBetweenDates(int groupId, LocalDate startDate, LocalDate endDate) {
        Groupe groupe = groupeRepository.findById(groupId).orElse(null);
        if (groupe != null && groupe.getMessages() != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // Set end time to 23:59:59 to include all messages on the end date

            return (int) groupe.getMessages().stream()
                    .filter(messageInfo -> {
                        LocalDateTime messageDateTime = messageInfo.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        return !messageDateTime.isBefore(startDateTime) && !messageDateTime.isAfter(endDateTime);
                    })
                    .count();
        }
        return 0;
    }


}
