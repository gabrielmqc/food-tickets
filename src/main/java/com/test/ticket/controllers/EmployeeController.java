package com.test.ticket.controllers;


import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.useCases.employee.CreateEmployeeUseCase;
import com.test.ticket.application.useCases.employee.GetAllEmployeesUseCase;
import com.test.ticket.domain.exceptions.BusinessRuleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin("*")
public class EmployeeController {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final GetAllEmployeesUseCase getAllEmployeesUseCase;

    public EmployeeController(CreateEmployeeUseCase createEmployeeUseCase, GetAllEmployeesUseCase getAllEmployeesUseCase) {
        this.createEmployeeUseCase = createEmployeeUseCase;
        this.getAllEmployeesUseCase = getAllEmployeesUseCase;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeResponseDTO) throws BusinessRuleException {
        EmployeeDTO newEmployee = createEmployeeUseCase.invoke(employeeResponseDTO);
        URI location = URI.create("/api/employees/" + newEmployee.id());
        return ResponseEntity.created(location).body(newEmployee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> listAllEmployees () {
        List<EmployeeDTO> employeeDTOS = getAllEmployeesUseCase.invoke();
        return ResponseEntity.ok(employeeDTOS);
    }
}
