package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

public record ReservationRequest(
        @NotNull(message = "La date de début est obligatoire")
        @FutureOrPresent(message = "La date de début ne peut pas être dans le passé")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotNull(message = "Le statut est obligatoire")
        StatutReservation statut,

        @NotNull(message = "L'identifiant du client est obligatoire")
        Long clientId,

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
