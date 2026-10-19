package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.EmployeService;
import tn.esprit.autoloc.web.dto.EmployeRequest;
import tn.esprit.autoloc.web.dto.EmployeResponse;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeResponse> create(@Valid @RequestBody EmployeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.create(request));
    }

    @GetMapping
    public List<EmployeResponse> findAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeResponse findById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    @PutMapping("/{id}")
    public EmployeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeRequest request) {
        return employeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeService.delete(id);
    }
}
