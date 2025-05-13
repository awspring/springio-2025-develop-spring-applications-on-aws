package io.awspring.workshop.infrastructure;

import io.awspring.workshop.domain.Order;
import io.awspring.workshop.domain.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDbOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
        // todo: store order
    }

    @Override
    public Order findById(String id) {
        // todo: find order by id
        return null;
    }
}
