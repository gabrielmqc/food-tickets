package com.test.ticket.application.dtos.response;

import com.test.ticket.domain.enums.Situation;

import java.time.LocalDate;
import java.util.UUID;

public record TicketResponseDTO(UUID id, UUID employeId, Integer quantity, Situation situation, LocalDate alterationDate) {
}
