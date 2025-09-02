package com.test.ticket.infrastructure.services;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.test.ticket.application.contracts.IReportGenerator;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.repositories.EmployeeRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class ReportPdfService implements IReportGenerator {

    @Autowired
    EmployeeRepositoryJPA employeeRepositoryJPA;

    @Override
    public byte[] generateTicketsReport(List<TicketDTO> tickets) {


        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("Relatório de Tickets"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Funcionário");
            table.addCell("Quantidade");
            table.addCell("Data de Entrega");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            tickets.stream()
                    .sorted(Comparator.comparing(TicketDTO::alterationDate).reversed())
                    .forEach(ticket -> {
                        String employeeName = employeeRepositoryJPA.findById(ticket.employeeId())
                                .map(EmployeeEntity::getName)
                                .orElse("Desconhecido");

                        table.addCell(employeeName);
                        table.addCell(ticket.quantity().toString());
                        table.addCell(ticket.alterationDate().format(formatter));
                    });

            document.add(table);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}
