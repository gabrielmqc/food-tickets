package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
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
class UpdateTicketUseCaseTest {
    @Mock
    private ITicket repository;

    @Mock
    private TicketMapperBO mapperBO;

    @InjectMocks
    private UpdateTicketUseCase updateTicketUseCase;

    private UUID ticketId;
    private TicketDTO existingDTO;
    private TicketDTO newDataDTO;
    private TicketBO ticketBO;
    private TicketDTO updatedDTO;

    @BeforeEach
    void setUp() {
        ticketId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        UUID newEmployeeId = UUID.randomUUID();

        existingDTO = new TicketDTO(
                ticketId,
                employeeId,
                5,
                Situation.A,
                LocalDateTime.now().minusDays(1)
        );

        newDataDTO = new TicketDTO(
                null,
                newEmployeeId,
                10,
                Situation.I,
                null
        );

        EmployeeBO employeeBO = new EmployeeBO(employeeId, "John Doe", "12345678901",
                Situation.A, LocalDateTime.now(), List.of());

        ticketBO = new TicketBO(ticketId, employeeBO, 5, Situation.A, LocalDateTime.now());

        updatedDTO = new TicketDTO(
                ticketId,
                newEmployeeId,
                10,
                Situation.I,
                LocalDateTime.now()
        );
    }


    @Test
    void invoke_ShouldUpdateTicket_WhenTicketExists() {
        when(repository.getById(ticketId)).thenReturn(Optional.of(existingDTO));
        when(mapperBO.toBO(existingDTO)).thenReturn(ticketBO);
        when(mapperBO.toDTO(ticketBO)).thenReturn(updatedDTO);
        when(repository.update(updatedDTO, ticketId)).thenReturn(Optional.of(updatedDTO));

        TicketDTO result = updateTicketUseCase.invoke(newDataDTO, ticketId);

        assertNotNull(result);
        assertEquals(updatedDTO, result);

        assertEquals(10, ticketBO.getQuantity());
        assertEquals(Situation.I, ticketBO.getSituation());
    }


    @Test
    void invoke_ShouldThrowNotFoundException_WhenTicketDoesNotExist() {
        when(repository.getById(ticketId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> updateTicketUseCase.invoke(newDataDTO, ticketId));

        assertEquals("Ticket não encontrado", exception.getMessage());
        verify(repository, never()).update(any(), any());
    }

    @Test
    void invoke_ShouldOnlyUpdateQuantity_WhenOnlyQuantityProvided() {
        TicketDTO partialData = new TicketDTO(null, null, 8, null, null);
        when(repository.getById(ticketId)).thenReturn(Optional.of(existingDTO));
        when(mapperBO.toBO(existingDTO)).thenReturn(ticketBO);
        when(mapperBO.toDTO(ticketBO)).thenReturn(updatedDTO);
        when(repository.update(updatedDTO, ticketId)).thenReturn(Optional.of(updatedDTO));

        TicketDTO result = updateTicketUseCase.invoke(partialData, ticketId);

        assertNotNull(result);

        assertEquals(8, ticketBO.getQuantity());
        assertEquals(Situation.A, ticketBO.getSituation());
    }

}