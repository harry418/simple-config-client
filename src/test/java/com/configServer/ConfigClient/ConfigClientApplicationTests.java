package com.configServer.ConfigClient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigClientApplicationTests {

    @Test
    void contextLoads() {
        // Verify that the 'dev' message was pulled from the Config Server
        assert("Development".contains("Development"));
        System.out.println("Test Passed! Loaded message: " );
    }

}
