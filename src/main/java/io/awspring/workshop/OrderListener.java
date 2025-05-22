package io.awspring.workshop;

import io.awspring.cloud.sns.sms.SnsSmsOperations;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.workshop.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderListener.class);

    private final OrderRepository orderRepository;
    private final InvoiceFactory invoiceFactory;
    private final InvoiceRepository invoiceRepository;
    private final SnsSmsOperations snsSmsOperations;

    public OrderListener(OrderRepository orderRepository, InvoiceFactory invoiceFactory, InvoiceRepository invoiceRepository, SnsSmsOperations snsSmsOperations) {
        this.orderRepository = orderRepository;
        this.invoiceFactory = invoiceFactory;
        this.invoiceRepository = invoiceRepository;
        this.snsSmsOperations = snsSmsOperations;
    }

    @SqsListener(queueNames = "order-queue")
    void handle(OrderCreated event) {
        LOGGER.info("Received event: {}", event);
        Order order = orderRepository.findById(event.orderId());
        Invoice invoice = invoiceFactory.invoiceFor(order);
        invoiceRepository.store(invoice);
        snsSmsOperations.send("+48691948188", "invoice ready");
    }
}
