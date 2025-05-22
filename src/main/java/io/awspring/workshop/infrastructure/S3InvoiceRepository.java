package io.awspring.workshop.infrastructure;

import io.awspring.workshop.domain.Invoice;
import io.awspring.workshop.domain.InvoiceRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class S3InvoiceRepository implements InvoiceRepository {

    @Override
    public void store(Invoice invoice) {
        // todo: upload invoice to S3
    }

    @Override
    public Resource findByOrderId(String orderId) {
        // todo: download invoice from S3
        return null;
    }

    @Override
    public URL findGetUrlByOrderId(String orderId) {
        // todo: generate signed url
        return null;
    }
}
