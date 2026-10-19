package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.EquipementService;
import tn.esprit.autoloc.web.dto.EquipementRequest;
import tn.esprit.autoloc.web.dto.EquipementResponse;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final EquipementService equipementService;

    @PostMapping
    public ResponseEntity<EquipementResponse> create(@Valid @RequestBody EquipementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipementService.create(request));
    }

    @GetMapping
    public List<EquipementResponse> findAll() {
        return equipementService.findAll();
    }

    @GetMapping("/{id}")
    public EquipementResponse findById(@PathVariable Long id) {
        return equipementService.findById(id);
    }

    @PutMapping("/{id}")
    public EquipementResponse update(@PathVariable Long id, @Valid @RequestBody EquipementRequest request) {
        return equipementService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        equipementService.delete(id);
    }
}
