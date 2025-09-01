package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.domain.exceptions.BusinessRuleException;
import com.test.ticket.domain.models.EmployeeBO;

import java.time.LocalDateTime;
import java.util.List;

public class CreateEmployeeUseCase {
    private final IEmployee repository;
    private final EmployeeMapperBO employeeMapperBO;


    public CreateEmployeeUseCase(IEmployee repository, EmployeeMapperBO employeeMapperBO) {
        this.repository = repository;
        this.employeeMapperBO = employeeMapperBO;
    }

    public EmployeeDTO invoke (EmployeeDTO requestDTO) throws BusinessRuleException {

        EmployeeBO employeeBO = new EmployeeBO(
                null,
                requestDTO.name(),
                requestDTO.cpf(),
                requestDTO.situation(),
                LocalDateTime.now(),
                List.of()
        );

        employeeBO.validateForCreation();

        return repository.create(employeeMapperBO.toDTO(employeeBO));
    }
}
