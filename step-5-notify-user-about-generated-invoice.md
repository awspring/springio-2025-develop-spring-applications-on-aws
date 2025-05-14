# Step 5: Notify user about generated invoice

Once invoice is generated, user should receive an SMS text message about it.

## Add dependency to Spring Cloud AWS SNS integration

Add a dependency to `build.gradle`:

```
implementation 'io.awspring.cloud:spring-cloud-aws-starter-sns'
```

## Send SMS

Spring Cloud AWS comes with a high level `SnsSmsOperations` for sending text messages.

Update `OrderListener` class to send a text message. Watch LocalStack logs to see if the integration works.

```java
snsSmsOperations.send("+48555444333", "invoice ready");
```

### 
[Next](step-6-use-localstack-with-testcontainers.md)