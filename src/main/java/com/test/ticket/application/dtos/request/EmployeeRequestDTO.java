package com.test.ticket.application.dtos.request;

import com.test.ticket.domain.enums.Situation;

public record EmployeeRequestDTO(String name, String cpf, Situation situation) {
}
