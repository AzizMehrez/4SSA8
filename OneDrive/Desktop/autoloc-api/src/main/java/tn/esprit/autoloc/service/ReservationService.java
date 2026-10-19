package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.web.dto.ReservationRequest;
import tn.esprit.autoloc.web.dto.ReservationResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final VehiculeService vehiculeService;

    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = new Reservation();
        apply(reservation, request);
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream().map(ReservationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ReservationResponse findById(Long id) {
        return ReservationResponse.from(getEntity(id));
    }

    public ReservationResponse update(Long id, ReservationRequest request) {
        Reservation reservation = getEntity(id);
        apply(reservation, request);
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    public void delete(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Réservation introuvable avec l'id " + id);
        }
        reservationRepository.deleteById(id);
    }

    public Reservation getEntity(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable avec l'id " + id));
    }

    private void apply(Reservation reservation, ReservationRequest request) {
        reservation.setDateDebut(request.dateDebut());
        reservation.setDateFin(request.dateFin());
        reservation.setStatut(request.statut());
        reservation.setClient(clientService.getEntity(request.clientId()));
        reservation.setVehicule(vehiculeService.getEntity(request.vehiculeId()));
    }
}
