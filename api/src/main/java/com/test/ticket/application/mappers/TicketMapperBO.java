package com.test.ticket.application.mappers;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TicketMapperBO {

    private final IEmployee employee;

    public TicketMapperBO(IEmployee employee) {
        this.employee = employee;
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

    public EmployeeBO getEmployeeBO(UUID employeeId) {
        return employee.getById(employeeId)
                .map(e -> new EmployeeBO(
                        e.id(),
                        e.name(),
                        e.cpf(),
                        e.situation(),
                        e.alterationDate(),
                        List.of()
                ))
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
    }

    public TicketBO toBO(TicketDTO dto) {
        return new TicketBO(
                dto.id(),
                getEmployeeBO(dto.employeeId()),
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
