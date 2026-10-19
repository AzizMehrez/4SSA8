package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Contrat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratResponse(
        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        Boolean valide,
        Long reservationId
) {
    public static ContratResponse from(Contrat contrat) {
        return new ContratResponse(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.getValide(),
                contrat.getReservation() != null ? contrat.getReservation().getIdReservation() : null
        );
    }
}
