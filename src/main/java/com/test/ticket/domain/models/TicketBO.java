package com.test.ticket.domain.models;

import com.test.ticket.domain.enums.Situation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class TicketBO {

    private final UUID id;
    private final EmployeeBO employee;
    private Integer quantity;
    private Situation situation;
    private LocalDate deliveryDate;

    public void add() {
        quantity++;
        lastUpdate();
    }

    public void lastUpdate () {
        deliveryDate = LocalDate.now();
    }
}
