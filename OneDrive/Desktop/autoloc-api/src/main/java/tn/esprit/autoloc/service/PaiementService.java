package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.PaiementRepository;
import tn.esprit.autoloc.web.dto.PaiementRequest;
import tn.esprit.autoloc.web.dto.PaiementResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final ContratService contratService;

    public PaiementResponse create(PaiementRequest request) {
        Paiement paiement = new Paiement();
        apply(paiement, request);
        return PaiementResponse.from(paiementRepository.save(paiement));
    }

    @Transactional(readOnly = true)
    public List<PaiementResponse> findAll() {
        return paiementRepository.findAll().stream().map(PaiementResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public PaiementResponse findById(Long id) {
        return PaiementResponse.from(getEntity(id));
    }

    public PaiementResponse update(Long id, PaiementRequest request) {
        Paiement paiement = getEntity(id);
        apply(paiement, request);
        return PaiementResponse.from(paiementRepository.save(paiement));
    }

    public void delete(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paiement introuvable avec l'id " + id);
        }
        paiementRepository.deleteById(id);
    }

    private Paiement getEntity(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable avec l'id " + id));
    }

    private void apply(Paiement paiement, PaiementRequest request) {
        paiement.setMontant(request.montant());
        paiement.setDatePaiement(request.datePaiement());
        paiement.setModePaiement(request.modePaiement());
        paiement.setContrat(contratService.getEntity(request.contratId()));
    }
}
