package io.awspring.workshop;

import io.awspring.workshop.domain.Order;
import io.awspring.workshop.infrastructure.PdfBoxInvoiceFactory;
import io.awspring.workshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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
}
