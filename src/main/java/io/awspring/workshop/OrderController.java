package io.awspring.workshop;

import io.awspring.workshop.domain.InvoiceRepository;
import io.awspring.workshop.domain.Order;
import io.awspring.workshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
        // orderService.createOrder(order);
        orderService.createOrderInSns(order);
        return ResponseEntity.ok("Order saved with ID: " + order.getOrderId());
    }

    @GetMapping("/{orderId}/invoice")
    public RedirectView invoice(@PathVariable String orderId) {
        var url = invoiceRepository.findGetUrlByOrderId(orderId);
        return new RedirectView(url.toString());
    }
}
