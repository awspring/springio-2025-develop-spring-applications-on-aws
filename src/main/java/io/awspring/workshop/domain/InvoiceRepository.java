package io.awspring.workshop.domain;

import org.springframework.core.io.Resource;

import java.net.URL;

public interface InvoiceRepository {
    void store(Invoice invoice);

    Resource findByOrderId(String orderId);

    URL findGetUrlByOrderId(String orderId);
}
