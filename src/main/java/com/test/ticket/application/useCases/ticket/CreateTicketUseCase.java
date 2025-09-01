package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.domain.models.TicketBO;


public class CreateTicketUseCase {

    private final ITicket repository;
    private final TicketMapperBO ticketMapperBO;

    public CreateTicketUseCase(ITicket repository, TicketMapperBO ticketMapperBO) {
        this.repository = repository;
        this.ticketMapperBO = ticketMapperBO;
    }

    public TicketDTO invoke(TicketDTO ticketDTO) {
        TicketBO ticketBO = ticketMapperBO.toBO(ticketDTO);

        ticketBO.validateForCreation();

        return repository.create(ticketMapperBO.toDTO(ticketBO));
    }
}
