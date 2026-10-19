package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MaintenanceRequest(
        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        LocalDate dateFin,

        @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
        String description,

        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        Long vehiculeId
) {
    @AssertTrue(message = "La date de fin doit être postérieure ou égale à la date de début")
    public boolean isPeriodeValide() {
        if (dateDebut == null || dateFin == null) {
            return true;
        }
        return !dateFin.isBefore(dateDebut);
    }
}
