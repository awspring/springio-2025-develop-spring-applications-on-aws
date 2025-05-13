package io.awspring.workshop;

import io.awspring.cloud.dynamodb.DynamoDbOperations;
import io.awspring.workshop.domain.Order;
import io.awspring.workshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class WorkshopApplicationTests {
    @Autowired
    private OrderService orderService;
    @Autowired
    private DynamoDbOperations dynamoDbOperations;

    @Test
    void contextLoads() {
        Order order = new Order("1", "foo", "usId", BigDecimal.valueOf(20));

        orderService.createOrder(order);

        Order loadedOrder = dynamoDbOperations.load(Key.builder().partitionValue("1").build(), Order.class);
        assertThat(loadedOrder).usingRecursiveComparison().isEqualTo(order);
    }

}
