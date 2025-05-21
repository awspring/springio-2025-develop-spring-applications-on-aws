package io.awspring.workshop;

import io.awspring.workshop.domain.InvoiceRepository;
import io.awspring.workshop.domain.Order;
import io.awspring.workshop.service.OrderService;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.net.URL;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final InvoiceRepository invoiceRepository;

    public OrderController(OrderService orderService, InvoiceRepository invoiceRepository) {
        this.orderService = orderService;
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Welcome to the Spring Cloud AWS Workshop!");
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.ok("Order saved with ID: " + order.getOrderId());
    }

    @GetMapping("/{orderId}/invoice")
    public ResponseEntity<Resource> invoice(@PathVariable String orderId) throws Exception {
        try {
            Resource resource = invoiceRepository.findByOrderId(orderId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment()
                                    .filename(resource.getFilename())
                                    .build().toString())
                    .body(resource);
        } catch (NoSuchKeyException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "{orderId}/invoice", produces = "application/json")
    public InvoiceDto invoiceDto(@PathVariable String orderId) {
        return new InvoiceDto(invoiceRepository.findGetUrlByOrderId(orderId));
    }

    public record InvoiceDto(URL url) {}
}
