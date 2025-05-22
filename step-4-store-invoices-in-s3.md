# Step 4: Store invoices in S3

## Add dependency to Spring Cloud AWS S3 integration

Add a dependency to `build.gradle`:

```
implementation 'io.awspring.cloud:spring-cloud-aws-starter-s3'
```

## Store invoices with `S3Operations`

`S3Operations` is a high level interface for storing and retrieving objects and files from S3.

Update `S3InvoiceRepository#store` method to use `S3Operations#upload`. Since invoice is generated to a byte array, use `ByteArrayInputStream`:

<details>
<summary>Solution</summary>

```java
@Override
public void store(Invoice invoice) {
    s3Operations.upload("invoices", invoice.fileName(), new ByteArrayInputStream(invoice.content()));
}
```
</details>

Similarly, implement `findByOrderId` to download a file from S3:

<details>
<summary>Solution</summary>

```java
@Override
public Resource findByOrderId(String orderId) {
    return s3Operations.download("invoices", Invoice.fileNameFor(orderId));
}
```
</details>

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

What if downloaded invoice does not exist on S3? Update `OrderController` to handle such case and return `404` when invoice is not found. 

**Hint** take a look at `S3Resource#exists` method.

<details>
<summary>Solution</summary>

```java
@GetMapping("/{orderId}/invoice")
public ResponseEntity<Resource> invoice(@PathVariable String orderId) throws Exception {
    Resource resource = invoiceRepository.findByOrderId(orderId);
    if (resource.exists()) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(resource.getFilename())
                                .build().toString())
                .body(resource);
    } else {
        return ResponseEntity.notFound().build();
    }
}
```

</details>

## Signed URL

Instead of transferring the invoice from S3 through the application, in `GET /order/{orderId}/invoice` controller method, create a signed `GET` url to download an image and redirect a request to the generated endpoint.

<details>
<summary>Solution</summary>

```java
@Override
public URL findGetUrlByOrderId(String orderId) {
    return s3Operations.createSignedGetURL("invoices", Invoice.fileNameFor(orderId), Duration.ofMinutes(10));
}
```
</details>

### 
[Next](step-5-notify-user-about-generated-invoice.md)