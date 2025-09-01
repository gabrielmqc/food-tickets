package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;

import java.util.Optional;
import java.util.UUID;

public class ActivateTicketUseCase {
    private final ITicket ticketRepository;
    private final TicketMapperBO mapperBO;

    public ActivateTicketUseCase(ITicket ticketRepository, TicketMapperBO mapperBO) {
        this.ticketRepository = ticketRepository;

        this.mapperBO = mapperBO;
    }

    public void invoke(UUID id) {
        Optional<TicketDTO> ticketDTO = ticketRepository.getById(id);

        if (ticketDTO.isEmpty()) {
            throw new NotFoundException("Ticket não encontrado");
        }

        TicketBO ticketBO = mapperBO.toBO(ticketDTO.get());

        ticketBO.activate();
        ticketBO.lastUpdate();

        ticketRepository.save(mapperBO.toDTO(ticketBO));
    }
}
