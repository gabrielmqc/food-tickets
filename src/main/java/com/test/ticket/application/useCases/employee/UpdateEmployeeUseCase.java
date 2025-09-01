package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class UpdateEmployeeUseCase {

    @Autowired
    private final IEmployee repository;

    public UpdateEmployeeUseCase(IEmployee repository) {
        this.repository = repository;
    }

    public EmployeeDTO invoke(EmployeeDTO employeeDTO,UUID id){
        Optional<EmployeeDTO> existingEmployee = repository.getById(id);
        if (existingEmployee.isEmpty()) {
            throw  new NotFoundException("Funcionário não encontrado");
        }

        return repository.update(employeeDTO,id);
    }
}
