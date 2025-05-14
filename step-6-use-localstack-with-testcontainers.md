# Step 6: Use LocalStack with Testcontainers

## Add dependency to Spring Cloud AWS Testcontainers

Add a dependency to `build.gradle`:

```
testImplementation 'io.awspring.cloud:spring-cloud-aws-testcontainers'
```

## Define a container 

Define a LocalStack container in `TestcontainersConfiguration`:

```java
@ServiceConnection
@Bean
LocalStackContainer localStackContainer() {
    return new LocalStackContainer(DockerImageName.parse("localstack/localstack:4.4.0"))
            .withCopyFileToContainer(MountableFile.forClasspathResource("init-localstack.sh", 0744), "/etc/localstack/init/ready.d/init-aws.sh");
}
```

Move `init-localstack.sh` to `src/test/resources`.

## Remove credentials and region configuration from `application.yml`

Since `@ServiceConnection` configures Spring Boot application to connect to LocalStack, there is no need anymore to specify credentials, region and endpoint in `application.yml`

```yml
spring:
  cloud:
    aws:
      s3:
        path-style-access-enabled: true
```

## Delete `docker-compose.yml`

We are not going to use `docker-compose.yml`. To start application locally, use `TestSpringCloudAwsWorkshopApplication` instead as it uses containers configured in `TestcontainersConfiguration` class.