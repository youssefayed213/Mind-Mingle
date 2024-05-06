package com.example.mindmingle.service;

import com.example.mindmingle.entities.Inscription;

import java.util.List;

public interface IInscripitionService {
    Inscription addInscription(Inscription inscription);
    Inscription updateInscription(Inscription inscription);
    Void removeInscription(int idInscription);
    Inscription retriveInscription(int idInscription);
    List<Inscription> retriveAllInscription();
}
