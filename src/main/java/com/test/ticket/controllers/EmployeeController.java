package com.test.ticket.controllers;

import com.test.ticket.application.dtos.request.EmployeeRequestDTO;
import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.application.useCases.employee.CreateEmployeeUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin("*")
public class EmployeeController {

    private final CreateEmployeeUseCase createEmployeeUseCase;

    public EmployeeController(CreateEmployeeUseCase createEmployeeUseCase) {
        this.createEmployeeUseCase = createEmployeeUseCase;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO employeeResponseDTO){
        EmployeeResponseDTO newEmployee = createEmployeeUseCase.invoke(employeeResponseDTO);
        URI location = URI.create("/api/employees/" + newEmployee.id());
        return ResponseEntity.created(location).body(newEmployee);
    }
}
