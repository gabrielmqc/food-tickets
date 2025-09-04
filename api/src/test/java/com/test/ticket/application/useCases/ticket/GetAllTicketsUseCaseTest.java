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
class GetAllTicketsUseCaseTest {

    @Mock
    private ITicket repository;

    @InjectMocks
    private GetAllTicketsUseCase getAllTicketsUseCase;

    private List<TicketDTO> tickets;

    @BeforeEach
    void setUp() {
        tickets = List.of(
                new TicketDTO(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        5,
                        Situation.A,
                        LocalDateTime.now()
                ),
                new TicketDTO(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        3,
                        Situation.I,
                        LocalDateTime.now()
                )
        );
    }

    @Test
    void invoke_ShouldReturnAllTickets() {
        when(repository.getAll()).thenReturn(tickets);

        List<TicketDTO> result = getAllTicketsUseCase.invoke();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(tickets, result);
        verify(repository).getAll();
    }

    @Test
    void invoke_ShouldReturnEmptyList_WhenNoTickets() {
        when(repository.getAll()).thenReturn(List.of());

        List<TicketDTO> result = getAllTicketsUseCase.invoke();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).getAll();
    }
}