package com.example.mindmingle.services;
import java.time.LocalDate;
import java.util.Date;

import com.example.mindmingle.entities.Groupe;

import java.util.List;
import java.util.Set;

public interface IGroupe {
    public Groupe addGroupe(Groupe groupe);
    public Groupe upadateGroupe(Groupe groupe);
    void removeGroupe(Integer idGroupe);
    public Groupe retrieveGroupe(Integer idGroupe);
    public List<Groupe> retrieveAllGroupe();

    Groupe assignGroupeToCat(int idGroupe, int idCatGroupe);
    Groupe addMemberToGroupe(int groupId, int userId);
    Groupe deleteMemberFromGroupe(int groupId, int userId);


    Set<Groupe.MessageInfo> retrieveGroupMessages(Integer groupId);


    List<Object[]> getUsersWithDescriptions();

    int countMembers(int groupId);

    int countMessagesOnDate(int groupId, LocalDate date);
    int countMessagesBetweenDates(int groupId, LocalDate startDate,LocalDate endDate);
}
