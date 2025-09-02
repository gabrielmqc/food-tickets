package com.test.ticket.application.dtos;

import com.test.ticket.domain.enums.Situation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EmployeeDTO(UUID id, String name, String cpf, Situation situation, LocalDateTime alterationDate, List<UUID> ticketsIds) {
}
