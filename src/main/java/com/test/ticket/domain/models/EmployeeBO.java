package com.test.ticket.domain.models;

import com.test.ticket.domain.enums.Situation;
import lombok.AllArgsConstructor;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class EmployeeBO {

    private final UUID id;
    private String name;
    private String cpf;
    private Situation situation;
    private LocalDate alterationDate;


    public void activate() {
        this.situation = Situation.A;
    }

    public void deactivate() {
        this.situation = Situation.I;
    }

}
