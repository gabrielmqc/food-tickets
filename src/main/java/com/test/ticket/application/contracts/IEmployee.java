package com.test.ticket.application.contracts;

import com.test.ticket.application.dtos.request.EmployeeRequestDTO;
import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.domain.models.EmployeeBO;

import java.util.List;
import java.util.UUID;

public interface IEmployee extends IGeneralEntity<EmployeeResponseDTO, EmployeeBO>{

}
