package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.ModePaiement;
import tn.esprit.autoloc.domain.Paiement;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementResponse(
        Long idPaiement,
        BigDecimal montant,
        LocalDate datePaiement,
        ModePaiement modePaiement,
        Long contratId
) {
    public static PaiementResponse from(Paiement paiement) {
        return new PaiementResponse(
                paiement.getIdPaiement(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getModePaiement(),
                paiement.getContrat() != null ? paiement.getContrat().getIdContrat() : null
        );
    }
}
