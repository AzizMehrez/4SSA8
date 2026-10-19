package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.RoleEmploye;

public record EmployeResponse(
        Long idEmploye,
        String nom,
        String prenom,
        RoleEmploye role,
        Long agenceId
) {
    public static EmployeResponse from(Employe employe) {
        return new EmployeResponse(
                employe.getIdEmploye(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getRole(),
                employe.getAgence() != null ? employe.getAgence().getIdAgence() : null
        );
    }
}
