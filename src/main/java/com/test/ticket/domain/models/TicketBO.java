package com.test.ticket.domain.models;

import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.exceptions.BusinessRuleException;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TicketBO extends AbstractModel {

    private EmployeeBO employee;
    private Integer quantity;

    public TicketBO(UUID id,EmployeeBO employee, Integer quantity, Situation situation, LocalDateTime alterationDate) {
        super(id, situation, alterationDate);
        this.quantity = quantity;
        this.employee = employee;
    }

    @Override
    public void validateForCreation() throws BusinessRuleException {
        super.validateForCreation();
    }
}
