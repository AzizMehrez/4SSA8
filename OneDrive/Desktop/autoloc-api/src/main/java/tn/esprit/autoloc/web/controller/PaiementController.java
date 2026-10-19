package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.PaiementService;
import tn.esprit.autoloc.web.dto.PaiementRequest;
import tn.esprit.autoloc.web.dto.PaiementResponse;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping
    public ResponseEntity<PaiementResponse> create(@Valid @RequestBody PaiementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paiementService.create(request));
    }

    @GetMapping
    public List<PaiementResponse> findAll() {
        return paiementService.findAll();
    }

    @GetMapping("/{id}")
    public PaiementResponse findById(@PathVariable Long id) {
        return paiementService.findById(id);
    }

    @PutMapping("/{id}")
    public PaiementResponse update(@PathVariable Long id, @Valid @RequestBody PaiementRequest request) {
        return paiementService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        paiementService.delete(id);
    }
}
