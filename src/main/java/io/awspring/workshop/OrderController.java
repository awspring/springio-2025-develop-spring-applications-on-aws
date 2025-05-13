package io.awspring.workshop;

import io.awspring.workshop.infrastructure.PdfBoxInvoiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final PdfBoxInvoiceFactory invoiceService;

    public OrderController(PdfBoxInvoiceFactory invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Welcome to the Spring Cloud AWS Workshop!");
    }
}
