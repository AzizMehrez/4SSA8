package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratRequest(
        @NotNull(message = "La date de signature est obligatoire")
        @PastOrPresent(message = "La date de signature ne peut pas être dans le futur")
        LocalDate dateSignature,

        @NotNull(message = "Le montant total est obligatoire")
        @DecimalMin(value = "0.01", message = "Le montant total doit être supérieur à 0")
        BigDecimal montantTotal,

        @NotNull(message = "Le champ valide est obligatoire")
        Boolean valide,

        @NotNull(message = "L'identifiant de la réservation est obligatoire")
        Long reservationId
) {
}
