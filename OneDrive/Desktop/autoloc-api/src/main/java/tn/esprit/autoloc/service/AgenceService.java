package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.web.dto.AgenceRequest;
import tn.esprit.autoloc.web.dto.AgenceResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceService {

    private final AgenceRepository agenceRepository;

    public AgenceResponse create(AgenceRequest request) {
        Agence agence = new Agence();
        apply(agence, request);
        return AgenceResponse.from(agenceRepository.save(agence));
    }

    @Transactional(readOnly = true)
    public List<AgenceResponse> findAll() {
        return agenceRepository.findAll().stream().map(AgenceResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AgenceResponse findById(Long id) {
        return AgenceResponse.from(getEntity(id));
    }

    public AgenceResponse update(Long id, AgenceRequest request) {
        Agence agence = getEntity(id);
        apply(agence, request);
        return AgenceResponse.from(agenceRepository.save(agence));
    }

    public void delete(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence introuvable avec l'id " + id);
        }
        agenceRepository.deleteById(id);
    }

    public Agence getEntity(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence introuvable avec l'id " + id));
    }

    private void apply(Agence agence, AgenceRequest request) {
        agence.setNom(request.nom());
        agence.setVille(request.ville());
        agence.setAdresse(request.adresse());
        agence.setTelephone(request.telephone());
    }
}
