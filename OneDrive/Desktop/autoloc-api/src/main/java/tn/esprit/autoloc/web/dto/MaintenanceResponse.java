package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Maintenance;

import java.time.LocalDate;

public record MaintenanceResponse(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        Long vehiculeId
) {
    public static MaintenanceResponse from(Maintenance maintenance) {
        return new MaintenanceResponse(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                maintenance.getVehicule() != null ? maintenance.getVehicule().getIdVehicule() : null
        );
    }
}
