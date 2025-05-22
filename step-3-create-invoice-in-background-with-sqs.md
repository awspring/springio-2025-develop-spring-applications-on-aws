# Step 3: Create invoice in a background with SQS

## Add dependency to Spring Cloud AWS SQS integration

Add a dependency to `build.gradle`:

```
implementation 'io.awspring.cloud:spring-cloud-aws-starter-sqs'
```

## Publish `OrderCreated` event from `OrderService`

When order is persisted in DynamoDB, application has to generate the invoice. Generating the invoice can be an expensive operation and should happen in the background. For that, we can use SQS.

1. Publish the `OrderCreated` message containing an order id.
2. Listen for this message (potentially in another microservice) and generate an invoice

Add a dependency to `SqsTemplate` in `OrderService` and update the `createOrder` method:

```java
public void createOrder(Order order) {
    orderRepository.save(order);
    sqsTemplate.send("order-queue", new OrderCreated(order.getOrderId()));
}
```

## Listen for `OrderCreated` event

Update `OrderListener` to listen from SQS. Annotate `handle` meethod with:

```java
@SqsListener(queueNames = "order-queue")
```

## Bonus tasks!!

Time to update `OrderListener` to make our application more robust and performant!

1. Change default visibility timeout of messages processed by `@SqsListener` to 200.


```java
@SqsListener(queueNames = "order-queue", messageVisibilitySeconds = "200")
```

2. Increase throughput of `@SqsListener` by increasing number of in concurrent messages to 20 and increase max messages polled to 10

```java
@SqsListener(queueNames = "order-queue", maxConcurrentMessages = "30",  maxMessagesPerPoll = "10")
```

### 
[Next](step-4-store-invoices-in-s3.md)