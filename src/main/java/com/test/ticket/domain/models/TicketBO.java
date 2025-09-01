package com.test.ticket.domain.models;

import com.test.ticket.domain.enums.Situation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class TicketBO extends AbstractModel {


    private Integer quantity;

    public TicketBO(UUID id, Situation situation, LocalDate alterationDate, Integer quantity) {
        super(id, situation, alterationDate);
        this.quantity = quantity;
    }

    public void add() {
        quantity++;
        super.lastUpdate();
    }

}
