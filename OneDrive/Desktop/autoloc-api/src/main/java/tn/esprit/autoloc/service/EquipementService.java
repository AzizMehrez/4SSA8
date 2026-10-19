package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.web.dto.EquipementRequest;
import tn.esprit.autoloc.web.dto.EquipementResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementService {

    private final EquipementRepository equipementRepository;

    public EquipementResponse create(EquipementRequest request) {
        Equipement equipement = new Equipement();
        equipement.setLibelle(request.libelle());
        return EquipementResponse.from(equipementRepository.save(equipement));
    }

    @Transactional(readOnly = true)
    public List<EquipementResponse> findAll() {
        return equipementRepository.findAll().stream().map(EquipementResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public EquipementResponse findById(Long id) {
        return EquipementResponse.from(getEntity(id));
    }

    public EquipementResponse update(Long id, EquipementRequest request) {
        Equipement equipement = getEntity(id);
        equipement.setLibelle(request.libelle());
        return EquipementResponse.from(equipementRepository.save(equipement));
    }

    public void delete(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Équipement introuvable avec l'id " + id);
        }
        equipementRepository.deleteById(id);
    }

    public Equipement getEntity(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Équipement introuvable avec l'id " + id));
    }

    public Set<Equipement> getEntities(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }
        List<Equipement> found = equipementRepository.findAllById(ids);
        if (found.size() != ids.size()) {
            throw new ResourceNotFoundException("Un ou plusieurs équipements sont introuvables");
        }
        return new HashSet<>(found);
    }
}
