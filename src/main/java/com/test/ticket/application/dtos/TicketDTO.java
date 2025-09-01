package com.test.ticket.application.dtos;

import com.test.ticket.domain.enums.Situation;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketDTO(UUID id, UUID employeId, Integer quantity, Situation situation, LocalDateTime alterationDate) {
}
