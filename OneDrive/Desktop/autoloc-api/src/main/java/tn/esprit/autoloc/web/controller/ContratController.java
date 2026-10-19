package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.ContratService;
import tn.esprit.autoloc.web.dto.ContratRequest;
import tn.esprit.autoloc.web.dto.ContratResponse;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final ContratService contratService;

    @PostMapping
    public ResponseEntity<ContratResponse> create(@Valid @RequestBody ContratRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratService.create(request));
    }

    @GetMapping
    public List<ContratResponse> findAll() {
        return contratService.findAll();
    }

    @GetMapping("/{id}")
    public ContratResponse findById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    @PutMapping("/{id}")
    public ContratResponse update(@PathVariable Long id, @Valid @RequestBody ContratRequest request) {
        return contratService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contratService.delete(id);
    }
}
