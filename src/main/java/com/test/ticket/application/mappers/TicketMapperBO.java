package com.test.ticket.application.mappers;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketMapperBO {

    private final IEmployee employee;
    private final EmployeeMapperBO employeeMapperBO;

    public TicketMapperBO(IEmployee employee, EmployeeMapperBO employeeMapperBO) {
        this.employee = employee;
        this.employeeMapperBO = employeeMapperBO;
    }

    public TicketDTO toDTO(TicketBO bo) {
        return new TicketDTO(
                bo.getId(),
                bo.getEmployee().getId(),
                bo.getQuantity(),
                bo.getSituation(),
                bo.getAlterationDate()
        );
    }

    public TicketBO toBO(TicketDTO dto) {

        Optional<EmployeeDTO> employeeDTO = employee.getById(dto.employeId());

        if (employeeDTO.isEmpty()) {

            throw new NotFoundException("Funcionário não encontrado");
        }

        return new TicketBO(
                dto.id(),
                employeeMapperBO.toBO(employeeDTO.get()),
                dto.quantity(),
                dto.situation(),
                dto.alterationDate()
        );
    }

    public List<TicketDTO> toDTOList(List<TicketBO> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketBO> toBOList(List<TicketDTO> dtos) {
        return dtos.stream()
                .map(this::toBO)
                .collect(Collectors.toList());
    }
}
