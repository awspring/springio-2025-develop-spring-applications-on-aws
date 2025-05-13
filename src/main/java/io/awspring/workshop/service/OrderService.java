package io.awspring.workshop.service;

import io.awspring.workshop.domain.Order;
import io.awspring.workshop.domain.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
        // todo: publish OrderCreated to "order-queue" queue in SQS
    }
}
