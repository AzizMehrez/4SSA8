package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record VehiculeResponse(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long agenceId,
        Set<Long> equipementIds
) {
    public static VehiculeResponse from(Vehicule vehicule) {
        Set<Long> equipementIds = vehicule.getEquipements() == null
                ? Set.of()
                : vehicule.getEquipements().stream()
                .map(Equipement::getIdEquipement)
                .collect(Collectors.toSet());

        return new VehiculeResponse(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                vehicule.getAgence() != null ? vehicule.getAgence().getIdAgence() : null,
                equipementIds
        );
    }
}
