package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.BusinessException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.ContratRepository;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.web.dto.ContratRequest;
import tn.esprit.autoloc.web.dto.ContratResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratService {

    private final ContratRepository contratRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public ContratResponse create(ContratRequest request) {
        Reservation reservation = reservationService.getEntity(request.reservationId());
        if (reservation.getContrat() != null) {
            throw new BusinessException("Cette réservation possède déjà un contrat");
        }

        Contrat contrat = new Contrat();
        contrat.setDateSignature(request.dateSignature());
        contrat.setMontantTotal(request.montantTotal());
        contrat.setValide(request.valide());

        reservation.setContrat(contrat);
        Reservation saved = reservationRepository.save(reservation);
        Contrat savedContrat = saved.getContrat();
        return new ContratResponse(
                savedContrat.getIdContrat(),
                savedContrat.getDateSignature(),
                savedContrat.getMontantTotal(),
                savedContrat.getValide(),
                saved.getIdReservation()
        );
    }

    @Transactional(readOnly = true)
    public List<ContratResponse> findAll() {
        return contratRepository.findAll().stream().map(ContratResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ContratResponse findById(Long id) {
        return ContratResponse.from(getEntity(id));
    }

    public ContratResponse update(Long id, ContratRequest request) {
        Contrat contrat = getEntity(id);
        Reservation reservation = reservationService.getEntity(request.reservationId());

        if (reservation.getContrat() != null
                && !reservation.getContrat().getIdContrat().equals(id)) {
            throw new BusinessException("Cette réservation possède déjà un autre contrat");
        }

        contrat.setDateSignature(request.dateSignature());
        contrat.setMontantTotal(request.montantTotal());
        contrat.setValide(request.valide());

        if (contrat.getReservation() != null
                && !contrat.getReservation().getIdReservation().equals(reservation.getIdReservation())) {
            contrat.getReservation().setContrat(null);
        }
        reservation.setContrat(contrat);

        contratRepository.save(contrat);
        reservationRepository.save(reservation);
        return ContratResponse.from(contrat);
    }

    public void delete(Long id) {
        Contrat contrat = getEntity(id);
        Reservation reservation = contrat.getReservation();
        if (reservation != null) {
            reservation.setContrat(null);
            reservationRepository.save(reservation);
        }
        contratRepository.delete(contrat);
    }

    public Contrat getEntity(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat introuvable avec l'id " + id));
    }
}
