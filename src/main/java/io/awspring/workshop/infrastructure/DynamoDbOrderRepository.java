package io.awspring.workshop.infrastructure;

import io.awspring.workshop.domain.Order;
import io.awspring.workshop.domain.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDbOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
        // todo: implement using DynamoDbOperations
    }
}
