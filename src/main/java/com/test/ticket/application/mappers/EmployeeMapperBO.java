package com.test.ticket.application.mappers;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;

import java.util.List;


public class EmployeeMapperBO {

    private final ITicket ticketRepository;

    public EmployeeMapperBO(ITicket ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public EmployeeDTO toDTO(EmployeeBO bo) {
        return new EmployeeDTO(
                bo.getId(),
                bo.getName(),
                bo.getCpf(),
                bo.getSituation(),
                bo.getAlterationDate(),
                bo.getTickets().stream().map(TicketBO::getId).toList()
        );
    }

    public EmployeeBO toBO(EmployeeDTO dto) {
        List<TicketBO> ticketBOS = dto.ticketsIds().stream().map(ticketRepository::getById).toList();
        return new EmployeeBO(
                dto.id(),
                dto.name(),
                dto.cpf(),
                dto.situation(),
                dto.alterationDate(),
                dto.ticketsIds()
        );
    }

}
