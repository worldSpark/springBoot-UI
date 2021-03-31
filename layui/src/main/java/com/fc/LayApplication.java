package com.fc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.fc.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = "com.fc")
public class LayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayApplication.class, args);
    }

}
