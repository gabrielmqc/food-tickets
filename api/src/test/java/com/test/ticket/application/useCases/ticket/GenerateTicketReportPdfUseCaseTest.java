package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.IReportGenerator;
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
class GenerateTicketReportPdfUseCaseTest {

    @Mock
    private IReportGenerator reportGenerator;

    @InjectMocks
    private GenerateTicketReportPdfUseCase generateTicketReportPdfUseCase;

    private List<TicketDTO> ticketDTOS;
    private byte[] pdfBytes;

    @BeforeEach
    void setUp() {
        ticketDTOS = List.of(
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

        pdfBytes = new byte[]{1, 2, 3, 4, 5};
    }

    @Test
    void invoke_ShouldGeneratePdfReport() {
        when(reportGenerator.generateTicketsReport(ticketDTOS)).thenReturn(pdfBytes);

        byte[] result = generateTicketReportPdfUseCase.invoke(ticketDTOS);

        assertNotNull(result);
        assertEquals(pdfBytes, result);
        verify(reportGenerator).generateTicketsReport(ticketDTOS);
    }

    @Test
    void invoke_ShouldHandleEmptyList() {
        when(reportGenerator.generateTicketsReport(List.of())).thenReturn(new byte[0]);

        byte[] result = generateTicketReportPdfUseCase.invoke(List.of());

        assertNotNull(result);
        assertEquals(0, result.length);
        verify(reportGenerator).generateTicketsReport(List.of());
    }
}