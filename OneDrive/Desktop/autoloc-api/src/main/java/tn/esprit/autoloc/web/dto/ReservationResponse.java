package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

public record ReservationResponse(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutReservation statut,
        Long clientId,
        Long vehiculeId,
        Long contratId
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                reservation.getClient() != null ? reservation.getClient().getIdClient() : null,
                reservation.getVehicule() != null ? reservation.getVehicule().getIdVehicule() : null,
                reservation.getContrat() != null ? reservation.getContrat().getIdContrat() : null
        );
    }
}
