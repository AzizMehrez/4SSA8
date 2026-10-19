package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.EmployeRepository;
import tn.esprit.autoloc.web.dto.EmployeRequest;
import tn.esprit.autoloc.web.dto.EmployeResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceService agenceService;

    public EmployeResponse create(EmployeRequest request) {
        Employe employe = new Employe();
        apply(employe, request);
        return EmployeResponse.from(employeRepository.save(employe));
    }

    @Transactional(readOnly = true)
    public List<EmployeResponse> findAll() {
        return employeRepository.findAll().stream().map(EmployeResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public EmployeResponse findById(Long id) {
        return EmployeResponse.from(getEntity(id));
    }

    public EmployeResponse update(Long id, EmployeRequest request) {
        Employe employe = getEntity(id);
        apply(employe, request);
        return EmployeResponse.from(employeRepository.save(employe));
    }

    public void delete(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employé introuvable avec l'id " + id);
        }
        employeRepository.deleteById(id);
    }

    private Employe getEntity(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé introuvable avec l'id " + id));
    }

    private void apply(Employe employe, EmployeRequest request) {
        employe.setNom(request.nom());
        employe.setPrenom(request.prenom());
        employe.setRole(request.role());
        employe.setAgence(agenceService.getEntity(request.agenceId()));
    }
}
