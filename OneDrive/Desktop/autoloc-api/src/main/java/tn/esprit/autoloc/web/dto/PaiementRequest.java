package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import tn.esprit.autoloc.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementRequest(
        @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
        BigDecimal montant,

        @NotNull(message = "La date de paiement est obligatoire")
        @PastOrPresent(message = "La date de paiement ne peut pas être dans le futur")
        LocalDate datePaiement,

        @NotNull(message = "Le mode de paiement est obligatoire")
        ModePaiement modePaiement,

        @NotNull(message = "L'identifiant du contrat est obligatoire")
        Long contratId
) {
}
