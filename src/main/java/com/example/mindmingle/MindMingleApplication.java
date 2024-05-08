package com.example.mindmingle;






import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling

@SpringBootApplication
public class MindMingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindMingleApplication.class, args);

    }

}
