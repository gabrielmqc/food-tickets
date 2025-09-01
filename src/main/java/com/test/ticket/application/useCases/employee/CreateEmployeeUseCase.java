package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.request.EmployeeRequestDTO;
import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.domain.exceptions.BusinessRuleException;
import com.test.ticket.domain.models.EmployeeBO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CreateEmployeeUseCase {
    private final IEmployee repository;
    private final EmployeeMapperBO employeeMapperBO;


    public CreateEmployeeUseCase(IEmployee repository, EmployeeMapperBO employeeMapperBO) {
        this.repository = repository;
        this.employeeMapperBO = employeeMapperBO;
    }

    public EmployeeResponseDTO invoke (EmployeeRequestDTO requestDTO) throws BusinessRuleException {

        EmployeeBO employeeBO = new EmployeeBO(
                null,
                requestDTO.name(),
                requestDTO.cpf(),
                requestDTO.situation(),
                LocalDate.now(),
                List.of()
        );

        employeeBO.validateForCreation();

        return repository.create(employeeBO);
    }
}
