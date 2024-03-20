package com.pi.projet.Services;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.entities.Projet;

public interface ProjetService {

    public ResponseProjet createProject(RequestProjet project);
}
