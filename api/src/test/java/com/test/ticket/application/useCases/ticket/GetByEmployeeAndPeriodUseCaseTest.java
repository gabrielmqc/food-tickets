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
class GetByEmployeeAndPeriodUseCaseTest {

    @Mock
    private ITicket repository;

    @InjectMocks
    private GetByEmployeeAndPeriodUseCase getByEmployeeAndPeriodUseCase;

    private UUID employeeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<TicketDTO> tickets;

    @BeforeEach
    void setUp() {
        employeeId = UUID.randomUUID();
        startDate = LocalDateTime.now().minusDays(7);
        endDate = LocalDateTime.now();

        tickets = List.of(
                new TicketDTO(
                        UUID.randomUUID(),
                        employeeId,
                        5,
                        Situation.A,
                        LocalDateTime.now().minusDays(3)
                )
        );
    }

    @Test
    void invoke_ShouldReturnTicketsForEmployeeAndPeriod() {
        when(repository.getByEmployeeAndPeriod(employeeId, startDate, endDate)).thenReturn(tickets);

        List<TicketDTO> result = getByEmployeeAndPeriodUseCase.invoke(employeeId, startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tickets, result);
        verify(repository).getByEmployeeAndPeriod(employeeId, startDate, endDate);
    }

    @Test
    void invoke_ShouldReturnEmptyList_WhenNoTicketsFound() {
        when(repository.getByEmployeeAndPeriod(employeeId, startDate, endDate)).thenReturn(List.of());

        List<TicketDTO> result = getByEmployeeAndPeriodUseCase.invoke(employeeId, startDate, endDate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).getByEmployeeAndPeriod(employeeId, startDate, endDate);
    }
}