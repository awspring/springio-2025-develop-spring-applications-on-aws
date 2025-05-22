package io.awspring.workshop.service;

import io.awspring.cloud.sns.core.SnsNotification;
import io.awspring.cloud.sns.core.SnsOperations;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.workshop.domain.Order;
import io.awspring.workshop.domain.OrderCreated;
import io.awspring.workshop.domain.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final SqsTemplate sqsTemplate;
    private final SnsOperations snsOperations;

    public OrderService(OrderRepository orderRepository, SqsTemplate sqsTemplate, SnsOperations snsOperations) {
        this.orderRepository = orderRepository;
        this.sqsTemplate = sqsTemplate;
        this.snsOperations = snsOperations;
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
        sqsTemplate.send("order-queue", new OrderCreated(order.getOrderId()));
    }

    public void createOrderInSns(Order order) {
        orderRepository.save(order);
        snsOperations.sendNotification("order-created-topic", SnsNotification.of(new OrderCreated(order.getOrderId())));
    }
}
