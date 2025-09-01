package com.test.ticket.application.dtos.request;

import com.test.ticket.domain.enums.Situation;

import java.util.UUID;

public record TicketRequestDTO(UUID employeeId, Situation situation) {
}
