package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;
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
class CreateTicketUseCaseTest {

    @Mock
    private ITicket repository;

    @Mock
    private TicketMapperBO ticketMapperBO;

    @Mock
    private IEmployee employee;

    @InjectMocks
    private CreateTicketUseCase createTicketUseCase;

    private UUID employeeId;
    private EmployeeDTO employeeDTO;
    private TicketDTO requestDTO;
    private TicketDTO responseDTO;
    private TicketBO ticketBO;

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

        requestDTO = new TicketDTO(
                null,
                employeeId,
                5,
                Situation.A,
                null
        );

        responseDTO = new TicketDTO(
                UUID.randomUUID(),
                employeeId,
                5,
                Situation.A,
                LocalDateTime.now()
        );

        ticketBO = mock(TicketBO.class);
    }

    @Test
    void invoke_ShouldCreateTicket_WhenValidData() {
        when(employee.getById(employeeId)).thenReturn(Optional.of(employeeDTO));
        when(ticketMapperBO.toDTO(any(TicketBO.class))).thenReturn(responseDTO);
        when(repository.create(responseDTO)).thenReturn(responseDTO);

        TicketDTO result = createTicketUseCase.invoke(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(employee).getById(employeeId);
        verify(repository).create(responseDTO);
    }

    @Test
    void invoke_ShouldThrowNotFoundException_WhenEmployeeDoesNotExist() {
        when(employee.getById(employeeId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> createTicketUseCase.invoke(requestDTO));

        assertEquals("Funcionário não encontrado", exception.getMessage());
        verify(repository, never()).create(any());
    }
}