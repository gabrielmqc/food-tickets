package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.domain.models.TicketBO;

import java.util.Optional;
import java.util.UUID;

public class DeactivateTicketUseCase {
    private final ITicket ticketRepository;
    private final TicketMapperBO mapperBO;

    public DeactivateTicketUseCase(ITicket ticketRepository, TicketMapperBO mapperBO) {
        this.ticketRepository = ticketRepository;

        this.mapperBO = mapperBO;
    }

    public void invoke(UUID id) {
        Optional<TicketDTO> ticketDTO = ticketRepository.getById(id);

        if (ticketDTO.isEmpty()) {
            throw new NotFoundException("Ticket não encontrado");
        }

        TicketBO ticketBO = mapperBO.toBO(ticketDTO.get());

        ticketBO.deactivate();
        ticketBO.lastUpdate();

        ticketRepository.save(mapperBO.toDTO(ticketBO));
    }
}
