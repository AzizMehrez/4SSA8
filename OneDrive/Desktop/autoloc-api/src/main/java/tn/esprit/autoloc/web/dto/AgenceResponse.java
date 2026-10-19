package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Agence;

public record AgenceResponse(
        Long idAgence,
        String nom,
        String ville,
        String adresse,
        String telephone
) {
    public static AgenceResponse from(Agence agence) {
        return new AgenceResponse(
                agence.getIdAgence(),
                agence.getNom(),
                agence.getVille(),
                agence.getAdresse(),
                agence.getTelephone()
        );
    }
}
