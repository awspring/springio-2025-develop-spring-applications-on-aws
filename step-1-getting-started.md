# Step 1: Getting Started

## Check Java
You'll need Java 21 or newer for this workshop.

## Check Docker

In this workshop we use LocalStack Docker container, so you must have Docker environment running on your machine.

You can check the Docker availability by running:
```text
$ docker version
```

## Download the project

Clone the following project from GitHub to your computer:  
[https://github.com/awspring/springio-2025-develop-spring-applications-on-aws/](https://github.com/awspring/springio-2025-develop-spring-applications-on-aws/)

## Build the project to download the dependencies

With Gradle:
```text
./gradlew build -x check
```

## Start Localstack

```text
docker-compose up
```

### 
[Next](step-2-persist-orders-in-dynamodb.md)