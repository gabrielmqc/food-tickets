package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;

import java.util.Optional;
import java.util.UUID;

public class UpdateTicketUseCase {

    private final ITicket repository;
    private final TicketMapperBO mapperBO;


    public UpdateTicketUseCase(ITicket repository, TicketMapperBO mapperBO) {
        this.repository = repository;
        this.mapperBO = mapperBO;
    }

    public TicketDTO invoke(TicketDTO ticketDTO, UUID id) {
        Optional<TicketDTO> existingTicket = repository.getById(id);

        if (existingTicket.isEmpty()) {
            throw new NotFoundException("Ticket não encontrado");
        }

        TicketBO ticketBO = mapperBO.toBO(existingTicket.get());

        ticketBO.lastUpdate();

        TicketDTO validatedTicket = mapperBO.toDTO(ticketBO);

        return repository.update(validatedTicket, id).get();
    }
}
