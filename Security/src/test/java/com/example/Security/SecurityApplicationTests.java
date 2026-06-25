package com.example.Security;

import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

    private String jwtSecretKey;

    @Test
	void contextLoads() {

	}
    @PostConstruct
    public void check() {
        System.out.println("JWT Key = " + jwtSecretKey);
    }
}
