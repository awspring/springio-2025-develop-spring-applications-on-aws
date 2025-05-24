# Step 2: Persist orders in DynamoDB

## Add Spring Cloud AWS to the project

Add following dependencies to `build.gradle`:

```
implementation platform('io.awspring.cloud:spring-cloud-aws-dependencies:3.3.1')
implementation 'io.awspring.cloud:spring-cloud-aws-starter-dynamodb'
```

The `platform` dependency imports a BOM saving you from specifying a version for each Spring Cloud AWS dependency.

## Configure integration to use LocalStack

In `application.yml`

```yml
spring:
  cloud:
    aws:
      credentials:
        access-key: test
        secret-key: test
      region:
        static: us-east-1
      endpoint: http://localhost:4566
```

## Note on DynamoDB integration

Unlike most of the datastores implementation you might be familiar from the Spring Data project, Spring Cloud AWS DynamoDB integration does not provide full-fledged repository implementation for DynamoDB. Instead, it provides a lower level template-type class that is a higher level wrapper on top of `DynamoDBEnhancedClient` from AWS SDK.

## Turn `Order` into DynamoDbBean

- annotate `Order` class with `@DynamoDbBean`
- mark entity's primary key: annotate `getOrderId` with `@DynamoDbPartitionKey`
- `@DynamoDBBean` (unfortunately) requires setters and default constructor, update the class to conform with this requirements

## Persist orders with `DynamoDbOperations`

Update `DynamoDbOrderRepository#save` method to save entity through `DynamoDbOperations#save`. 

```java
@Override
public void save(Order order) {
    dynamoDbOperations.save(order);
}
```

## Implement `findById` method

```java
@Override
public Order findById(String id) {
    return dynamoDbOperations.load(Key.builder().partitionValue(id).build(), Order.class);
}
```

### 
[Next](step-3-create-invoice-in-background-with-sqs.md)