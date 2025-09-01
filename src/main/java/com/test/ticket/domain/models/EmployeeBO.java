package com.test.ticket.domain.models;

import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.exceptions.BusinessRuleException;
import com.test.ticket.domain.interfaces.Creation;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class EmployeeBO extends AbstractModel implements Creation{

    private String name;
    private String cpf;

    public EmployeeBO(UUID id, Situation situation, LocalDate alterationDate, String name, String cpf) {
        super(id, situation, alterationDate);
        this.name = name;
        this.cpf = cpf;
    }


}
