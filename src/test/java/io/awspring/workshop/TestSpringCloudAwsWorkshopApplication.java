package io.awspring.workshop;

import org.springframework.boot.SpringApplication;

public class TestSpringCloudAwsWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringCloudAwsWorkshopApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
