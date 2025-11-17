package com.abefas;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication  // Enables Spring Boot auto-configuration and component scanning
public class EcomApp {

    @Configuration
    public class JacksonConfig {

        @Bean
        public Hibernate6Module hibernateModule() {
            return new Hibernate6Module();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(EcomApp.class, args);
        System.out.println("E-commerce Spring Boot application started.");
    }



}
