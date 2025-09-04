package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;
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
class GetByEmployeeUseCaseTest {

    @Mock
    private ITicket repository;

    @InjectMocks
    private GetByEmployeeUseCase getByEmployeeUseCase;

    private UUID employeeId;
    private List<TicketDTO> tickets;

    @BeforeEach
    void setUp() {
        employeeId = UUID.randomUUID();
        tickets = List.of(
                new TicketDTO(
                        UUID.randomUUID(),
                        employeeId,
                        5,
                        Situation.A,
                        LocalDateTime.now()
                ),
                new TicketDTO(
                        UUID.randomUUID(),
                        employeeId,
                        3,
                        Situation.I,
                        LocalDateTime.now().minusDays(1)
                )
        );
    }

    @Test
    void invoke_ShouldReturnTicketsForEmployee() {
        when(repository.getByEmployeeId(employeeId)).thenReturn(tickets);

        List<TicketDTO> result = getByEmployeeUseCase.invoke(employeeId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(tickets, result);
        verify(repository).getByEmployeeId(employeeId);
    }

    @Test
    void invoke_ShouldReturnEmptyList_WhenNoTicketsForEmployee() {
        when(repository.getByEmployeeId(employeeId)).thenReturn(List.of());

        List<TicketDTO> result = getByEmployeeUseCase.invoke(employeeId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).getByEmployeeId(employeeId);
    }
}