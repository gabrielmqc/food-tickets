package com.test.ticket.controllers;

import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.useCases.ticket.*;
import org.aspectj.weaver.BCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin("*")
public class TicketController {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetAllTicketsUseCase getAllTicketsUseCase;
    private final UpdateTicketUseCase updateTicketUseCase;
    private final ActivateTicketUseCase activateTicketUseCase;
    private final DeactivateTicketUseCase deactivateTicketUseCase;

    public TicketController(CreateTicketUseCase createTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase, UpdateTicketUseCase updateTicketUseCase, ActivateTicketUseCase activateTicketUseCase, DeactivateTicketUseCase deactivateTicketUseCase) {
        this.createTicketUseCase = createTicketUseCase;
        this.getAllTicketsUseCase = getAllTicketsUseCase;
        this.updateTicketUseCase = updateTicketUseCase;
        this.activateTicketUseCase = activateTicketUseCase;
        this.deactivateTicketUseCase = deactivateTicketUseCase;
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

    @PutMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> updateEmployee (@RequestBody TicketDTO ticketDTO, @PathVariable UUID ticketId) {
        TicketDTO updatedTicket = updateTicketUseCase.invoke(ticketDTO, ticketId);
        return ResponseEntity.ok(updatedTicket);
    }

    @PatchMapping("/{ticketId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateTicket(@PathVariable UUID ticketId) {
        try {
            activateTicketUseCase.invoke(ticketId);
        } catch (BCException | NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/{ticketId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateTicket(@PathVariable UUID ticketId) {
        try {
            deactivateTicketUseCase.invoke(ticketId);
        } catch (BCException | NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
