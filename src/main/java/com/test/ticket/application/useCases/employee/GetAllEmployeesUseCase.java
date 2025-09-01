package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GetAllEmployeesUseCase {

    @Autowired
    private final IEmployee repository;


    public GetAllEmployeesUseCase(IEmployee repository) {
        this.repository = repository;
    }

    public List<EmployeeDTO> invoke () {
        return repository.getAll();
    }
}
