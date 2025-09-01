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
        Optional<TicketDTO> existingTicketOpt = repository.getById(id);
        if (existingTicketOpt.isEmpty()) {
            throw new NotFoundException("Ticket não encontrado");
        }

        TicketBO existingBO = mapperBO.toBO(existingTicketOpt.get());

        if (ticketDTO.employeeId() != null && !ticketDTO.employeeId().equals(existingBO.getEmployee().getId())) {
            existingBO.setEmployee(mapperBO.getEmployeeBO(ticketDTO.employeeId()));
        }

        if (ticketDTO.quantity() != null) {
            existingBO.setQuantity(ticketDTO.quantity());
        }

        if (ticketDTO.situation() != null) {
            existingBO.setSituation(ticketDTO.situation());
        }

        existingBO.lastUpdate();

        TicketDTO updatedDTO = mapperBO.toDTO(existingBO);
        return repository.update(updatedDTO, id).orElseThrow(() ->
                new NotFoundException("Erro ao atualizar ticket"));
    }
}
