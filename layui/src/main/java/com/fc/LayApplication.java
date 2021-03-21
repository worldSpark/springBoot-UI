package com.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = "com.fc")
public class LayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayApplication.class, args);
    }

}
