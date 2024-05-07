package com.example.mindmingle.services;

import com.example.mindmingle.entities.RendezVous;

import java.util.List;

public interface IRendezVous {
    RendezVous addRendezVous(RendezVous rendezVous);
    RendezVous updateRendezVous(Integer idUser, RendezVous rendezVous);
    void removeRendezVous(Integer idUser, Integer idRdv);
    RendezVous retrieveRendezVous(Integer idRdv);
    List<RendezVous> retreiveAllRendezVous();


    RendezVous retrieveRendezVousByUserIdAndId(int idUser, int idRdv);
}
