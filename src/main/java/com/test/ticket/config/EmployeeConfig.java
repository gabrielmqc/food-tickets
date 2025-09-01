package com.test.ticket.config;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.application.useCases.employee.CreateEmployeeUseCase;
import com.test.ticket.application.useCases.employee.GetAllEmployeesUseCase;
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
    public EmployeeMapperBO employeeMapperBO (){
        return new EmployeeMapperBO();

    }

    @Bean
    public EmployeeMapperEntity employeeMapperEntity() {
        return new EmployeeMapperEntity();
    }

}
