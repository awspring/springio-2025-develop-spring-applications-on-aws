package io.awspring.workshop;

import io.awspring.workshop.domain.InvoiceRepository;
import io.awspring.workshop.domain.Order;
import io.awspring.workshop.domain.OrderRepository;
import io.awspring.workshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class WorkshopApplicationTests {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @MockitoSpyBean
    private InvoiceRepository invoiceRepository;

    @Test
    void contextLoads() throws InterruptedException {
        Order order = new Order(UUID.randomUUID().toString(), "foo", "usId", BigDecimal.valueOf(20));

        orderService.createOrder(order);

        Order loadedOrder = orderRepository.findById(order.getOrderId());
        assertThat(loadedOrder).usingRecursiveComparison().isEqualTo(order);

        verify(invoiceRepository, timeout(500)).store(any());
    }

}
