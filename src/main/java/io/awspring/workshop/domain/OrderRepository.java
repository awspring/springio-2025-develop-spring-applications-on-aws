package io.awspring.workshop.domain;

public interface OrderRepository {
    void save(Order order);
    Order findById(String id);
}
