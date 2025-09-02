package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;

import java.util.List;

public class GetAllTicketsUseCase {

    private final ITicket repository;

    public GetAllTicketsUseCase(ITicket repository) {
        this.repository = repository;
    }

    public List<TicketDTO> invoke() {
        return repository.getAll();
    }
}
