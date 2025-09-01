package com.test.ticket.config;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.application.useCases.employee.*;
import com.test.ticket.infrastructure.mappers.EmployeeMapperEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfig {

    @Bean
    public CreateEmployeeUseCase createEmployeeUseCase(IEmployee repository, EmployeeMapperBO employeeMapperBO) {
        return new CreateEmployeeUseCase(repository, employeeMapperBO);
    }

    @Bean
    public GetAllEmployeesUseCase getAllEmployeesUseCase(IEmployee employee) {
        return new GetAllEmployeesUseCase(employee);
    }

    @Bean
    public UpdateEmployeeUseCase updateEmployeeUseCase (IEmployee employee, EmployeeMapperBO mapperBO) {
        return new UpdateEmployeeUseCase(employee,mapperBO);
    }

    @Bean
    public ActivateEmployeeUseCase activateEmployeeUseCase (IEmployee employee, EmployeeMapperBO mapperBO) {
        return new ActivateEmployeeUseCase(employee,mapperBO);
    }

    @Bean
    public DeactivateEmployeeUseCase deactivateEmployeeUseCase (IEmployee employee, EmployeeMapperBO mapperBO) {
        return new DeactivateEmployeeUseCase(employee,mapperBO);
    }

    @Bean
    public EmployeeMapperBO employeeMapperBO (ITicket ticket, TicketMapperBO ticketMapperBO){
        return new EmployeeMapperBO(ticket,ticketMapperBO);

    }

    @Bean
    public EmployeeMapperEntity employeeMapperEntity() {
        return new EmployeeMapperEntity();
    }

}
