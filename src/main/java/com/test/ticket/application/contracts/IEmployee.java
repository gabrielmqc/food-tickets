package com.test.ticket.application.contracts;

import com.test.ticket.application.dtos.request.EmployeeRequestDTO;
import com.test.ticket.application.dtos.response.EmployeeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IEmployee {

    EmployeeResponseDTO create(EmployeeRequestDTO employee);

    EmployeeResponseDTO update (UUID employeeId, EmployeeRequestDTO employee);

    List<EmployeeResponseDTO> listEmployees();
}
