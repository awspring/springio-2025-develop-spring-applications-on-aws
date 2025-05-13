package io.awspring.workshop.domain;

import org.springframework.core.io.Resource;

public interface InvoiceRepository {
    void store(Invoice invoice);

    Resource findByOrderId(String orderId);
}
