package com.example.mindmingle.service;

import com.example.mindmingle.entities.Evenement;
import com.example.mindmingle.entities.User;

import java.util.List;

public interface IEvenemntService {
    Evenement addEvenement(Evenement evenement ) ;
    Evenement updateEvenement(Evenement evenement);
    Void removeEvenement(int idEvent);
    //Evenement retriveEvenement(int idEvent);
    //Evenement notifyEvenement(Evenement evenement);
    List<Evenement> retriveAllEvenement();



    void checkAndNotifyUserForNewEvent(Evenement newEvent, User user);

    String registerUserToEvent(Integer eventId, Integer userId) throws Exception;

    //void checkAndNotifyUserForNewEvent(Evenement newEvent, Integer user);

    //Evenement notifyEvenement(Evenement evenement, Integer userId) throws Exception;
}
