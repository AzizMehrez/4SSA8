package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.AgenceService;
import tn.esprit.autoloc.web.dto.AgenceRequest;
import tn.esprit.autoloc.web.dto.AgenceResponse;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final AgenceService agenceService;

    @PostMapping
    public ResponseEntity<AgenceResponse> create(@Valid @RequestBody AgenceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.create(request));
    }

    @GetMapping
    public List<AgenceResponse> findAll() {
        return agenceService.findAll();
    }

    @GetMapping("/{id}")
    public AgenceResponse findById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    @PutMapping("/{id}")
    public AgenceResponse update(@PathVariable Long id, @Valid @RequestBody AgenceRequest request) {
        return agenceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        agenceService.delete(id);
    }
}
