package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.exceptions.BusinessRuleException;
import com.test.ticket.domain.models.EmployeeBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEmployeeUseCaseTest {

    @Mock
    private IEmployee repository;

    @Mock
    private EmployeeMapperBO employeeMapperBO;

    @InjectMocks
    private CreateEmployeeUseCase createEmployeeUseCase;

    private EmployeeDTO requestDTO;
    private EmployeeBO employeeBO;
    private EmployeeDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new EmployeeDTO(
                null,
                "John Doe",
                "12345678901",
                Situation.A,
                null,
                List.of()
        );

        employeeBO = mock(EmployeeBO.class);
        responseDTO = new EmployeeDTO(
                UUID.randomUUID(),
                "John Doe",
                "12345678901",
                Situation.A,
                LocalDateTime.now(),
                List.of()
        );
    }

    @Test
    void invoke_ShouldCreateEmployee_WhenValidData() throws BusinessRuleException {
        when(employeeMapperBO.toDTO(any(EmployeeBO.class))).thenReturn(responseDTO);
        when(repository.create(responseDTO)).thenReturn(responseDTO);

        EmployeeDTO result = createEmployeeUseCase.invoke(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(employeeBO, never()).validateForCreation();
    }

    @Test
    void invoke_ShouldThrowBusinessRuleException_WhenInvalidData() {
        assertThrows(BusinessRuleException.class, () -> {
            EmployeeBO invalidBO = new EmployeeBO(
                    null,
                    "",              // Nome inválido
                    "invalid",       // CPF inválido
                    Situation.I,     // Situação inválida para criação
                    LocalDateTime.now(),
                    List.of()
            );
            invalidBO.validateForCreation();
        });
    }

}