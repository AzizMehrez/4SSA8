package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.BusinessException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceService agenceService;
    private final EquipementService equipementService;

    public VehiculeResponse create(VehiculeRequest request) {
        assertUniqueImmatriculation(request.immatriculation(), null);
        Vehicule vehicule = new Vehicule();
        apply(vehicule, request);
        return VehiculeResponse.from(vehiculeRepository.save(vehicule));
    }

    @Transactional(readOnly = true)
    public List<VehiculeResponse> findAll() {
        return vehiculeRepository.findAll().stream().map(VehiculeResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public VehiculeResponse findById(Long id) {
        return VehiculeResponse.from(getEntity(id));
    }

    public VehiculeResponse update(Long id, VehiculeRequest request) {
        Vehicule vehicule = getEntity(id);
        assertUniqueImmatriculation(request.immatriculation(), id);
        apply(vehicule, request);
        return VehiculeResponse.from(vehiculeRepository.save(vehicule));
    }

    public void delete(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Véhicule introuvable avec l'id " + id);
        }
        vehiculeRepository.deleteById(id);
    }

    public Vehicule getEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule introuvable avec l'id " + id));
    }

    private void assertUniqueImmatriculation(String immatriculation, Long currentId) {
        boolean exists = currentId == null
                ? vehiculeRepository.existsByImmatriculation(immatriculation)
                : vehiculeRepository.existsByImmatriculationAndIdVehiculeNot(immatriculation, currentId);
        if (exists) {
            throw new BusinessException("Un véhicule existe déjà avec l'immatriculation " + immatriculation);
        }
    }

    private void apply(Vehicule vehicule, VehiculeRequest request) {
        vehicule.setImmatriculation(request.immatriculation());
        vehicule.setMarque(request.marque());
        vehicule.setModele(request.modele());
        vehicule.setCategorie(request.categorie());
        vehicule.setTarifJournalier(request.tarifJournalier());
        vehicule.setStatut(request.statut());
        vehicule.setAgence(agenceService.getEntity(request.agenceId()));
        vehicule.setEquipements(equipementService.getEntities(request.equipementIds()));
    }
}
