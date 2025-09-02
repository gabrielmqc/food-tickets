package com.test.ticket.controllers;

import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.useCases.ticket.*;
import org.aspectj.weaver.BCException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final GetByEmployeeAndPeriodUseCase getByEmployeeAndPeriodUseCase;
    private final GetByPeriodUseCase getByPeriodUseCase;
    private final GetByEmployeeUseCase getByEmployeeUseCase;
    private final GenerateTicketReportPdfUseCase generateTicketReportPdfUseCase;

    public TicketController(CreateTicketUseCase createTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase, UpdateTicketUseCase updateTicketUseCase, ActivateTicketUseCase activateTicketUseCase, DeactivateTicketUseCase deactivateTicketUseCase, GetByEmployeeAndPeriodUseCase getByEmployeeAndPeriodUseCase, GetByPeriodUseCase getByPeriodUseCase, GetByEmployeeUseCase getByEmployeeUseCase, GenerateTicketReportPdfUseCase generateTicketReportPdfUseCase) {
        this.createTicketUseCase = createTicketUseCase;
        this.getAllTicketsUseCase = getAllTicketsUseCase;
        this.updateTicketUseCase = updateTicketUseCase;
        this.activateTicketUseCase = activateTicketUseCase;
        this.deactivateTicketUseCase = deactivateTicketUseCase;
        this.getByEmployeeAndPeriodUseCase = getByEmployeeAndPeriodUseCase;
        this.getByPeriodUseCase = getByPeriodUseCase;
        this.getByEmployeeUseCase = getByEmployeeUseCase;
        this.generateTicketReportPdfUseCase = generateTicketReportPdfUseCase;
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
    public ResponseEntity<TicketDTO> updateEmployee(@RequestBody TicketDTO ticketDTO, @PathVariable UUID ticketId) {
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

    @GetMapping("/pdf/employee/{employeeId}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<byte[]> getByEmployeeAndDatePdf(@PathVariable UUID employeeId, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        List<TicketDTO> ticketDTOS = getByEmployeeAndPeriodUseCase.invoke(employeeId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        byte[] pdf = generateTicketReportPdfUseCase.invoke(ticketDTOS);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=relatorio-tickets.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/pdf/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<byte[]> getByDatePeriodPdf(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        List<TicketDTO> ticketDTOS = getByPeriodUseCase.invoke(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        byte[] pdf = generateTicketReportPdfUseCase.invoke(ticketDTOS);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=relatorio-tickets.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/pdf/employee/{employeeId}")
    public ResponseEntity<byte[]> getByEmployeePdf(@PathVariable UUID employeeId) {
        List<TicketDTO> ticketDTOS = getByEmployeeUseCase.invoke(employeeId);
        byte[] pdf = generateTicketReportPdfUseCase.invoke(ticketDTOS);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=relatorio-tickets.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/employee/{employeeId}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<TicketDTO>> getByEmployeeAndDate(@PathVariable UUID employeeId, @PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        List<TicketDTO> ticketDTOS = getByEmployeeAndPeriodUseCase.invoke(employeeId, startDate, endDate);
        return ResponseEntity.ok(ticketDTOS);
    }

    @GetMapping("/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<TicketDTO>> getByDatePeriod(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        List<TicketDTO> ticketDTOS = getByPeriodUseCase.invoke(startDate, endDate);
        return ResponseEntity.ok(ticketDTOS);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TicketDTO>> getByEmployee(@PathVariable UUID employeeId) {
        List<TicketDTO> ticketDTOS = getByEmployeeUseCase.invoke(employeeId);
        return ResponseEntity.ok(ticketDTOS);
    }


}
