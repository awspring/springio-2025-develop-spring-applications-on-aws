package io.awspring.workshop;

import org.springframework.boot.SpringApplication;

public class TestSpringCloudAwsWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.from(WorkshopApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
