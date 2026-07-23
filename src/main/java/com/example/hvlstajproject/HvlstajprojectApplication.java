package com.example.hvlstajproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.example.hvlstajproject"})
@ComponentScan(basePackages = {"com.example.hvlstajproject"})
@EnableJpaRepositories(basePackages = {"com.example.hvlstajproject"})
@SpringBootApplication
public class HvlstajprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HvlstajprojectApplication.class, args);
    }
}
