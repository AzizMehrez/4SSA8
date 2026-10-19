package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.MaintenanceRepository;
import tn.esprit.autoloc.web.dto.MaintenanceRequest;
import tn.esprit.autoloc.web.dto.MaintenanceResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehiculeService vehiculeService;

    public MaintenanceResponse create(MaintenanceRequest request) {
        Maintenance maintenance = new Maintenance();
        apply(maintenance, request);
        return MaintenanceResponse.from(maintenanceRepository.save(maintenance));
    }

    @Transactional(readOnly = true)
    public List<MaintenanceResponse> findAll() {
        return maintenanceRepository.findAll().stream().map(MaintenanceResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public MaintenanceResponse findById(Long id) {
        return MaintenanceResponse.from(getEntity(id));
    }

    public MaintenanceResponse update(Long id, MaintenanceRequest request) {
        Maintenance maintenance = getEntity(id);
        apply(maintenance, request);
        return MaintenanceResponse.from(maintenanceRepository.save(maintenance));
    }

    public void delete(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance introuvable avec l'id " + id);
        }
        maintenanceRepository.deleteById(id);
    }

    private Maintenance getEntity(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance introuvable avec l'id " + id));
    }

    private void apply(Maintenance maintenance, MaintenanceRequest request) {
        maintenance.setDateDebut(request.dateDebut());
        maintenance.setDateFin(request.dateFin());
        maintenance.setDescription(request.description());
        maintenance.setVehicule(vehiculeService.getEntity(request.vehiculeId()));
    }
}
