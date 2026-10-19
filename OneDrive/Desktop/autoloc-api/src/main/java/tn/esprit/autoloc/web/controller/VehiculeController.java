package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.VehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @PostMapping
    public ResponseEntity<VehiculeResponse> create(@Valid @RequestBody VehiculeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.create(request));
    }

    @GetMapping
    public List<VehiculeResponse> findAll() {
        return vehiculeService.findAll();
    }

    @GetMapping("/{id}")
    public VehiculeResponse findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @PutMapping("/{id}")
    public VehiculeResponse update(@PathVariable Long id, @Valid @RequestBody VehiculeRequest request) {
        return vehiculeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vehiculeService.delete(id);
    }
}
