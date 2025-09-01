package com.test.ticket.application.mappers;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;

import java.util.Optional;

public class TicketMapperBO {

    private final IEmployee employee;
    private final EmployeeMapperBO employeeMapperBO;

    public TicketMapperBO(IEmployee employee, EmployeeMapperBO employeeMapperBO) {
        this.employee = employee;
        this.employeeMapperBO = employeeMapperBO;
    }

    public TicketDTO toDTO(TicketBO bo){
        return new TicketDTO(
                bo.getId(),
                bo.getEmployee().getId(),
                bo.getQuantity(),
                bo.getSituation(),
                bo.getAlterationDate()
        );
    }

    public TicketBO toBO(TicketDTO dto) {

        Optional<EmployeeBO> employeeBO =employeeMapperBO employee.getById(dto.employeId());

        return new TicketBO(
                dto.id(),
                dto.employeId(),
                dto.quantity(),
                dto.situation(),
                dto.alterationDate()
        );
    }
}
