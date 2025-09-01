package com.test.ticket.application.dtos.response;

import com.test.ticket.domain.enums.Situation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record EmployeeResponseDTO(UUID id, String name, String cpf, Situation situation, LocalDate alterationDate, List<TicketResponseDTO> tickets) {
}
