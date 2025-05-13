package io.awspring.workshop.domain;

public interface InvoiceFactory {
    Invoice invoiceFor(Order order);
}
