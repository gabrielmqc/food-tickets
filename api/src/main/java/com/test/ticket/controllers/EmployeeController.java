package com.test.ticket.controllers;


import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.useCases.employee.*;
import com.test.ticket.domain.exceptions.BusinessRuleException;
import jakarta.validation.Valid;
import org.aspectj.weaver.BCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin("*")
public class EmployeeController {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final GetAllEmployeesUseCase getAllEmployeesUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final ActivateEmployeeUseCase activateEmployeeUseCase;
    private final DeactivateEmployeeUseCase deactivateEmployeeUseCase;

    public EmployeeController(CreateEmployeeUseCase createEmployeeUseCase, GetAllEmployeesUseCase getAllEmployeesUseCase, UpdateEmployeeUseCase updateEmployeeUseCase, ActivateEmployeeUseCase activateEmployeeUseCase, DeactivateEmployeeUseCase deactivateEmployeeUseCase) {
        this.createEmployeeUseCase = createEmployeeUseCase;
        this.getAllEmployeesUseCase = getAllEmployeesUseCase;
        this.updateEmployeeUseCase = updateEmployeeUseCase;
        this.activateEmployeeUseCase = activateEmployeeUseCase;
        this.deactivateEmployeeUseCase = deactivateEmployeeUseCase;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeResponseDTO) throws BusinessRuleException {
        EmployeeDTO newEmployee = createEmployeeUseCase.invoke(employeeResponseDTO);
        URI location = URI.create("/api/employees/" + newEmployee.id());
        return ResponseEntity.created(location).body(newEmployee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> listAllEmployees () {
        List<EmployeeDTO> employeeDTOS = getAllEmployeesUseCase.invoke();
        return ResponseEntity.ok(employeeDTOS);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee (@RequestBody EmployeeDTO employeeDTO, @PathVariable UUID employeeId) {
        EmployeeDTO updatedEmployee = updateEmployeeUseCase.invoke(employeeDTO, employeeId);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{employeeId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateEmployee(@PathVariable UUID employeeId) {
        try {
            activateEmployeeUseCase.invoke(employeeId);
        } catch (BCException | NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/{employeeId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateEmployee(@PathVariable UUID employeeId) {
        try {
            deactivateEmployeeUseCase.invoke(employeeId);
        } catch (BCException | NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
