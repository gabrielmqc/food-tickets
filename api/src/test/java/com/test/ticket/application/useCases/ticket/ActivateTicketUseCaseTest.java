package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.models.TicketBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivateTicketUseCaseTest {
    @Mock
    private ITicket ticketRepository;

    @Mock
    private TicketMapperBO mapperBO;

    @InjectMocks
    private ActivateTicketUseCase activateTicketUseCase;

    private UUID ticketId;
    private TicketDTO ticketDTO;
    private TicketBO ticketBO;

    @BeforeEach
    void setUp() {
        ticketId = UUID.randomUUID();
        ticketDTO = new TicketDTO(
                ticketId,
                UUID.randomUUID(),
                5,
                Situation.I,
                LocalDateTime.now()
        );

        ticketBO = mock(TicketBO.class);
    }

    @Test
    void invoke_ShouldActivateTicket_WhenTicketExists() {
        when(ticketRepository.getById(ticketId)).thenReturn(Optional.of(ticketDTO));
        when(mapperBO.toBO(ticketDTO)).thenReturn(ticketBO);
        when(mapperBO.toDTO(ticketBO)).thenReturn(ticketDTO);

        assertDoesNotThrow(() -> activateTicketUseCase.invoke(ticketId));

        verify(ticketBO).activate();
        verify(ticketRepository).save(ticketDTO);
    }

    @Test
    void invoke_ShouldThrowNotFoundException_WhenTicketDoesNotExist() {
        when(ticketRepository.getById(ticketId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> activateTicketUseCase.invoke(ticketId));

        assertEquals("Ticket não encontrado", exception.getMessage());
        verify(ticketRepository, never()).save(any());
    }
}