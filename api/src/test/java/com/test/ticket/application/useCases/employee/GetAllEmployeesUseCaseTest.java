package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.domain.enums.Situation;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllEmployeesUseCaseTest {

    @Mock
    private IEmployee repository;

    @InjectMocks
    private GetAllEmployeesUseCase getAllEmployeesUseCase;

    private List<EmployeeDTO> employees;

    @BeforeEach
    void setUp() {
        employees = List.of(
                new EmployeeDTO(
                        UUID.randomUUID(),
                        "John Doe",
                        "12345678901",
                        Situation.A,
                        LocalDateTime.now(),
                        List.of()
                ),
                new EmployeeDTO(
                        UUID.randomUUID(),
                        "Jane Smith",
                        "10987654321",
                        Situation.I,
                        LocalDateTime.now(),
                        List.of()
                )
        );
    }

    @Test
    void invoke_ShouldReturnAllEmployees() {
        when(repository.getAll()).thenReturn(employees);

        List<EmployeeDTO> result = getAllEmployeesUseCase.invoke();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(employees, result);
        verify(repository).getAll();
    }

    @Test
    void invoke_ShouldReturnEmptyList_WhenNoEmployees() {
        when(repository.getAll()).thenReturn(List.of());

        List<EmployeeDTO> result = getAllEmployeesUseCase.invoke();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).getAll();
    }
}