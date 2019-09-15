package com.dfyang.isolation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class IsolationApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsolationApplication.class, args);
    }

}
