package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.domain.models.EmployeeBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class UpdateEmployeeUseCase {


    private final IEmployee repository;
    private final EmployeeMapperBO mapperBO;

    public UpdateEmployeeUseCase(IEmployee repository, EmployeeMapperBO mapperBO) {
        this.repository = repository;
        this.mapperBO = mapperBO;
    }

    public EmployeeDTO invoke(EmployeeDTO employeeDTO,UUID id){
        Optional<EmployeeDTO> existingEmployee = repository.getById(id);

        if (existingEmployee.isEmpty()) {
            throw  new NotFoundException("Funcionário não encontrado");
        }

        EmployeeBO employeeBO = mapperBO.toBO(existingEmployee.get());

        employeeBO.lastUpdate();

        EmployeeDTO validatedEmployee = mapperBO.toDTO(employeeBO);

        return repository.update(validatedEmployee,id).get();
    }
}
