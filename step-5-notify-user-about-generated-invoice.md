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

## Fan-Out from SNS to SQS

To support hypothetical other listeners for `OrderCreated` event, instead of publishing a message directly to SQS queue, publish it instead to `order-created-topic` with `SnsOperations`.

<details>
<summary>Solution</summary>

```java
snsOperations.sendNotification("order-created-topic", SnsNotification.of(new OrderCreated(order.getOrderId())));
```
</details>

On the listener side, to tell the framework that this message comes with SNS, annotate `event` argument in `OrderListener#handle` with `@SnsNotificationMessage` and change the queue name to `order-created-queue`.

<details>
<summary>Solution</summary>

```java
@SqsListener(queueNames = "order-created-queue")
void handle(@SnsNotificationMessage OrderCreated event) {
    ...
}
```
</details>

### 
[Next](step-6-use-localstack-with-testcontainers.md)