package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.domain.models.EmployeeBO;

import java.util.Optional;
import java.util.UUID;

public class ActivateEmployeeUseCase {
    private final IEmployee employeeRepository;
    private final EmployeeMapperBO mapperBO;

    public ActivateEmployeeUseCase(IEmployee employeeRepository, EmployeeMapperBO mapperBO) {
        this.employeeRepository = employeeRepository;
        this.mapperBO = mapperBO;
    }

    public void invoke(UUID id) {
        Optional<EmployeeDTO> employeeDTO = employeeRepository.getById(id);

        if (employeeDTO.isEmpty()) {
            throw new NotFoundException("Funcionário não encontrado");
        }

        EmployeeBO employeeBO = mapperBO.toBO(employeeDTO.get());

        employeeBO.activate();
        employeeBO.lastUpdate();

        employeeRepository.save(mapperBO.toDTO(employeeBO));
    }
}
