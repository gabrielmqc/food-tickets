package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class CreateTicketUseCase {

    private final ITicket repository;
    private final TicketMapperBO ticketMapperBO;
    private final IEmployee employee;


    public CreateTicketUseCase(ITicket repository, TicketMapperBO ticketMapperBO, IEmployee employee) {
        this.repository = repository;
        this.ticketMapperBO = ticketMapperBO;
        this.employee = employee;
    }

    public TicketDTO invoke(TicketDTO ticketDTO) {
        Optional<EmployeeDTO> employeeDTO = employee.getById(ticketDTO.employeeId());

        if (employeeDTO.isEmpty()) {

            throw new NotFoundException("Funcionário não encontrado");
        }

        TicketBO ticketBO = new TicketBO(
                ticketDTO.id(),
                new EmployeeBO(
                        employeeDTO.get().id(),
                        employeeDTO.get().name(),
                        employeeDTO.get().cpf(),
                        employeeDTO.get().situation(),
                        employeeDTO.get().alterationDate(),
                        List.of()
                ),
                ticketDTO.quantity(),
                ticketDTO.situation(),
                LocalDateTime.now()
        );
        ticketBO.validateForCreation();

        System.out.println("ticket validado" + ticketBO);

        return repository.create(ticketMapperBO.toDTO(ticketBO));
    }
}
