package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.MaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceRequest;
import tn.esprit.autoloc.web.dto.MaintenanceResponse;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponse> create(@Valid @RequestBody MaintenanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.create(request));
    }

    @GetMapping
    public List<MaintenanceResponse> findAll() {
        return maintenanceService.findAll();
    }

    @GetMapping("/{id}")
    public MaintenanceResponse findById(@PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @PutMapping("/{id}")
    public MaintenanceResponse update(@PathVariable Long id, @Valid @RequestBody MaintenanceRequest request) {
        return maintenanceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        maintenanceService.delete(id);
    }
}
