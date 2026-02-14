package com.configServer.ConfigClient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigClientApplicationTests {

	@Value("${custom.message}")
    private String message;

    @Test
    void contextLoads() {
        // Verify that the 'dev' message was pulled from the Config Server
        assert(message.contains("Development"));
        System.out.println("Test Passed! Loaded message: " + message);
    }

}
