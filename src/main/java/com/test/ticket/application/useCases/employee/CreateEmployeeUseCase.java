package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.request.EmployeeRequestDTO;
import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.domain.models.EmployeeBO;

public class CreateEmployeeUseCase {
    private final IEmployee repository;
    private final EmployeeMapperBO employeeMapperBO;


    public CreateEmployeeUseCase(IEmployee repository, EmployeeMapperBO employeeMapperBO) {
        this.repository = repository;
        this.employeeMapperBO = employeeMapperBO;
    }

    public EmployeeResponseDTO invoke (EmployeeRequestDTO requestDTO) {

        EmployeeBO employeeBO = employeeMapperBO.toBO(requestDTO);

        employeeBO.validateForCreation();

        return repository.create(employeeBO);
    }
}
