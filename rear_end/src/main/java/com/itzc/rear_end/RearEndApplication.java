package com.itzc.rear_end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class RearEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(RearEndApplication.class, args);
    }

}
