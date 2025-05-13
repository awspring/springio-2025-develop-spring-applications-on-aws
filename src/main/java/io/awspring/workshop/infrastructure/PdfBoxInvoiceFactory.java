package io.awspring.workshop.infrastructure;

import io.awspring.workshop.domain.Invoice;
import io.awspring.workshop.domain.InvoiceFactory;
import io.awspring.workshop.domain.Order;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfBoxInvoiceFactory implements InvoiceFactory {

    @Override
    public Invoice invoiceFor(Order order) {
        return new Invoice("invoice-" + order.getOrderId() + ".pdf", generateInvoicePdf(order));
    }

    private byte[] generateInvoicePdf(Order order) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDFont helveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDFont helvetica = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDFont helveticaOblique = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);


            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(helveticaBold, 20);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750); // Top-left position
                contentStream.showText("Invoice");
                contentStream.endText();

                contentStream.setFont(helvetica, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Order ID: " + order.getOrderId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Product: " + order.getProductName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("User ID: " + order.getUserId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Amount: $" + String.format("%.2f", order.getAmount()));
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date: " + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                contentStream.endText();

                contentStream.setFont(helveticaOblique, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 600);
                contentStream.showText("Thank you for your purchase!");
                contentStream.endText();
            }

            // Save to ByteArrayOutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
