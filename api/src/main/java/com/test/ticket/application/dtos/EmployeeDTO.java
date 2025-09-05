package com.test.ticket.application.dtos;

import com.test.ticket.domain.enums.Situation;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EmployeeDTO(UUID id, String name, @CPF String cpf, Situation situation, LocalDateTime alterationDate, List<UUID> ticketsIds) {
}
