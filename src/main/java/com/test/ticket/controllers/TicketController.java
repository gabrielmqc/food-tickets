package com.test.ticket.controllers;

import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.useCases.ticket.CreateTicketUseCase;
import com.test.ticket.application.useCases.ticket.GetAllTicketsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin("*")
public class TicketController {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetAllTicketsUseCase getAllTicketsUseCase;

    public TicketController(CreateTicketUseCase createTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase) {
        this.createTicketUseCase = createTicketUseCase;
        this.getAllTicketsUseCase = getAllTicketsUseCase;
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        TicketDTO newTicket = createTicketUseCase.invoke(ticketDTO);
        URI location = URI.create("/api/ticket/" + newTicket.id());
        return ResponseEntity.created(location).body(newTicket);
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAll() {
        List<TicketDTO> ticketDTOS = getAllTicketsUseCase.invoke();
        return ResponseEntity.ok(ticketDTOS);
    }
}
