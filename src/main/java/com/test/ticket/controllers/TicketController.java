package com.test.ticket.controllers;

import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.application.useCases.ticket.CreateTicketUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin("*")
public class TicketController {

    private final CreateTicketUseCase createTicketUseCase;

    public TicketController(CreateTicketUseCase createTicketUseCase) {
        this.createTicketUseCase = createTicketUseCase;
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        TicketDTO newTicket = createTicketUseCase.invoke(ticketDTO);
        URI location = URI.create("/api/ticket/" + newTicket.id());
        return ResponseEntity.created(location).body(newTicket);
    }
}
