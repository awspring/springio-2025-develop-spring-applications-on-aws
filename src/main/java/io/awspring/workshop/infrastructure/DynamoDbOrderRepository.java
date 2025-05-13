package io.awspring.workshop.infrastructure;

import io.awspring.cloud.dynamodb.DynamoDbOperations;
import io.awspring.workshop.domain.Order;
import io.awspring.workshop.domain.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDbOrderRepository implements OrderRepository {
    private final DynamoDbOperations dynamoDbOperations;

    public DynamoDbOrderRepository(DynamoDbOperations dynamoDbOperations) {
        this.dynamoDbOperations = dynamoDbOperations;
    }

    @Override
    public void save(Order order) {
        dynamoDbOperations.save(order);
    }
}
