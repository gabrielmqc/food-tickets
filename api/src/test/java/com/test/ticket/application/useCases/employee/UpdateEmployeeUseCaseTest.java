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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateEmployeeUseCaseTest {

    @Mock
    private IEmployee repository;

    @Mock
    private EmployeeMapperBO mapperBO;

    @InjectMocks
    private UpdateEmployeeUseCase updateEmployeeUseCase;

    private UUID employeeId;
    private EmployeeDTO existingDTO;
    private EmployeeDTO newDataDTO;
    private EmployeeBO employeeBO;
    private EmployeeDTO updatedDTO;

    @BeforeEach
    void setUp() {
        employeeId = UUID.randomUUID();
        existingDTO = new EmployeeDTO(
                employeeId,
                "Old Name",
                "12345678901",
                Situation.A,
                LocalDateTime.now().minusDays(1),
                List.of()
        );

        newDataDTO = new EmployeeDTO(
                null,
                "New Name",
                "10987654321",
                null,
                null,
                List.of()
        );

        employeeBO = mock(EmployeeBO.class);
        updatedDTO = new EmployeeDTO(
                employeeId,
                "New Name",
                "10987654321",
                Situation.A,
                LocalDateTime.now(),
                List.of()
        );
    }

    @Test
    void invoke_ShouldUpdateEmployee_WhenEmployeeExists() {
        when(repository.getById(employeeId)).thenReturn(Optional.of(existingDTO));
        when(mapperBO.toBO(existingDTO)).thenReturn(employeeBO);
        when(mapperBO.toDTO(employeeBO)).thenReturn(updatedDTO);
        when(repository.update(updatedDTO, employeeId)).thenReturn(Optional.of(updatedDTO));

        EmployeeDTO result = updateEmployeeUseCase.invoke(newDataDTO, employeeId);

        assertNotNull(result);
        assertEquals(updatedDTO, result);
        verify(employeeBO).setName("New Name");
        verify(employeeBO).setCpf("10987654321");
        verify(employeeBO).lastUpdate();
    }

    @Test
    void invoke_ShouldThrowNotFoundException_WhenEmployeeDoesNotExist() {
        when(repository.getById(employeeId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> updateEmployeeUseCase.invoke(newDataDTO, employeeId));

        assertEquals("Funcionário não encontrado", exception.getMessage());
        verify(repository, never()).update(any(), any());
    }

    @Test
    void invoke_ShouldOnlyUpdateName_WhenOnlyNameProvided() {
        EmployeeDTO partialData = new EmployeeDTO(null, "Partial Name", null, null, null, List.of());
        when(repository.getById(employeeId)).thenReturn(Optional.of(existingDTO));
        when(mapperBO.toBO(existingDTO)).thenReturn(employeeBO);
        when(mapperBO.toDTO(employeeBO)).thenReturn(updatedDTO);
        when(repository.update(updatedDTO, employeeId)).thenReturn(Optional.of(updatedDTO));

        EmployeeDTO result = updateEmployeeUseCase.invoke(partialData, employeeId);

        assertNotNull(result);
        verify(employeeBO).setName("Partial Name");
        verify(employeeBO, never()).setCpf(any());
        verify(employeeBO).lastUpdate();
    }
}