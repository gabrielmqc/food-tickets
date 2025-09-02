package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;

import java.util.List;
import java.util.UUID;

public class GetByEmployeeUseCase {

    private final ITicket repository;


    public GetByEmployeeUseCase(ITicket repository) {
        this.repository = repository;
    }

    public List<TicketDTO> invoke(UUID employeeId) {
        return repository.getByEmployeeId(employeeId);
    }
}
