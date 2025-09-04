package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.models.EmployeeBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivateEmployeeUseCaseTest {
    @Mock
    private IEmployee employeeRepository;

    @Mock
    private EmployeeMapperBO mapperBO;

    @InjectMocks
    private ActivateEmployeeUseCase activateEmployeeUseCase;

    private UUID employeeId;
    private EmployeeDTO employeeDTO;
    private EmployeeBO employeeBO;

    @BeforeEach
    void setUp() {
        employeeId = UUID.randomUUID();
        employeeDTO = new EmployeeDTO(
                employeeId,
                "John Doe",
                "12345678901",
                Situation.I,
                LocalDateTime.now(),
                List.of()
        );

        employeeBO = mock(EmployeeBO.class);
    }

    @Test
    void invoke_ShouldActivateEmployee_WhenEmployeeExists() {
        when(employeeRepository.getById(employeeId)).thenReturn(Optional.of(employeeDTO));
        when(mapperBO.toBO(employeeDTO)).thenReturn(employeeBO);
        when(mapperBO.toDTO(employeeBO)).thenReturn(employeeDTO);

        assertDoesNotThrow(() -> activateEmployeeUseCase.invoke(employeeId));

        verify(employeeBO).activate();
        verify(employeeBO).lastUpdate();
        verify(employeeRepository).save(employeeDTO);
    }

    @Test
    void invoke_ShouldThrowNotFoundException_WhenEmployeeDoesNotExist() {
        when(employeeRepository.getById(employeeId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> activateEmployeeUseCase.invoke(employeeId));

        assertEquals("Funcionário não encontrado", exception.getMessage());
        verify(employeeRepository, never()).save(any());
    }
}