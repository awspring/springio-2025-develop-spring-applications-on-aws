# Step 4: Store invoices in S3

## Add dependency to Spring Cloud AWS S3 integration

Add a dependency to `build.gradle`:

```
implementation 'io.awspring.cloud:spring-cloud-aws-starter-s3'
```

## Store invoices with `S3Operations`

`S3Operations` is a high level interface for storing and retrieving objects and files from S3.

Update `S3InvoiceRepository#store` method to use `S3Operations#upload`. Since invoice is generated to a byte array, use `ByteArrayInputStream`:

```java
@Override
public void store(Invoice invoice) {
    s3Operations.upload("invoices", invoice.fileName(), new ByteArrayInputStream(invoice.content()));
}
```

Similarly, implement `findByOrderId` to download a file from S3:

```java
@Override
public Resource findByOrderId(String orderId) {
    return s3Operations.download("invoices", Invoice.fileNameFor(orderId));
}
```

## Enable `path-style-access`

LocalStack S3 integration does not work with S3 bucket subdomains (that AWS SDK uses by default), so we must switch to use path style access:

```yml
spring:
  cloud:
    aws:
      s3:
        path-style-access-enabled: true
```

## Handle file not found

What if downloaded invoice does not exist on S3? Update `findByOrderId` method to handle such case, as well as `OrderController` to return `404` when invoice is not found.

### 
[Next](step-5-notify-user-about-generated-invoice)