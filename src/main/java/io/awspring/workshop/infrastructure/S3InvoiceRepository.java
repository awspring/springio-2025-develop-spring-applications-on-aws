package io.awspring.workshop.infrastructure;

import io.awspring.cloud.s3.S3Operations;
import io.awspring.workshop.domain.Invoice;
import io.awspring.workshop.domain.InvoiceRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.time.Duration;

@Component
public class S3InvoiceRepository implements InvoiceRepository {
    private final S3Operations s3Operations;

    public S3InvoiceRepository(S3Operations s3Operations) {
        this.s3Operations = s3Operations;
    }

    @Override
    public void store(Invoice invoice) {
        s3Operations.upload("invoices", invoice.fileName(), new ByteArrayInputStream(invoice.content()));
    }

    @Override
    public Resource findByOrderId(String orderId) {
        return s3Operations.download("invoices", Invoice.fileNameFor(orderId));
    }

    @Override
    public URL findGetUrlByOrderId(String orderId) {
        return s3Operations.createSignedGetURL("invoices", Invoice.fileNameFor(orderId), Duration.ofMinutes(10));
    }
}
