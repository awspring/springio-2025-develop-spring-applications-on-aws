# Step 7: Create SQS listeners on demand

## Add dependency to Spring Cloud AWS SQS if it is not there already

Add a dependency to `build.gradle`:

```
implementation 'io.awspring.cloud:spring-cloud-aws-starter-sqs'
```

## Implement custom listener

Change the `InvoiceListener` class so it implements `MessageListener<Invoice>` interface. 
Inside the class, you will need to implement (override) method called onMessage that will process the messages when they arrive. 
This method should look same like one you'd normally mark with `@SqsListener`.

```java
public class InvoiceListener implements MessageListener<Invoice> {
    @Override
    public void onMessage(Message<Invoice> invoice) {
        System.out.println(invoice);
    }
}
```

## Create SQSContainer and start listening

Behind the scenes, every method annotated with `@SqsListener` has an associated container created automatically. 
This container manages the lifecycle of the listener, including polling messages from the queue, handling acknowledgments (if the default automatic mode is used), managing throughput and more...

However, when listeners are created dynamically at runtime, Spring Cloud AWS does not automatically create these containers. In such cases, the container must be created manually.

To support this, the following changes should be introduced to the `QueueManagingService` class.

```java
 @Autowired
 private SqsAsyncClient sqsAsyncClient;

 public String createAndStartListener(String queueName) {
     MessageListener<Invoice> myCustomListener = new InvoiceListener();

     SqsMessageListenerContainer<Invoice> container = SqsMessageListenerContainer.builder()
         .sqsAsyncClient(sqsAsyncClient)
        .messageListener(myCustomListener)
        .configure(config -> 
            config.backPressureMode(BackPressureMode.FIXED_HIGH_THROUGHPUT)
                  .messageVisibility(Duration.ofSeconds(160))
                  .maxMessagesPerPoll(10).maxConcurrentMessages(200))
        .queueNames(queueName)
        .build();
     container.start();
     containerCache.put(queueName, container);
     return container.getId();
}
```

As you can see, various configurations can be applied at the container level—for example, the maximum number of messages to fetch in a single poll, 
the maximum number of messages to process concurrently, backpressure settings, and more.

Once the container is created, it needs to be started to begin polling messages. Additionally, it should be stored in a map to maintain a reference for future use.

## Stop SQSContainer on demand

To stop the listening from the queue change the implementation of `stopListener` method:

```java
 public String stopListener(String queueName) {
    containerCache.get(queueName).stop();
    return containerCache.get(queueName).getId();
}
 ```