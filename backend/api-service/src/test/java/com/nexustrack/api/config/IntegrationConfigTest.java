package com.nexustrack.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationConfigTest {

    private IntegrationConfig integrationConfig;

    @BeforeEach
    void setUp() {
        integrationConfig = new IntegrationConfig();
    }

    @Test
    void load_parsesAllFields() {
        String yaml = "webhookUrl: https://hooks.example.com/nexustrack\n" +
                "timeoutSeconds: 30\n" +
                "retryEnabled: true\n" +
                "maxRetries: 3\n" +
                "slackChannel: '#engineering'\n";

        IntegrationConfig.IntegrationSettings settings = integrationConfig.load(yaml);

        assertEquals("https://hooks.example.com/nexustrack", settings.getWebhookUrl());
        assertEquals(30, settings.getTimeoutSeconds());
        assertTrue(settings.isRetryEnabled());
        assertEquals(3, settings.getMaxRetries());
        assertEquals("#engineering", settings.getSlackChannel());
    }

    @Test
    void load_handlesDisabledRetry() {
        String yaml = "webhookUrl: https://hooks.example.com\n" +
                "timeoutSeconds: 10\n" +
                "retryEnabled: false\n" +
                "maxRetries: 0\n" +
                "slackChannel: '#alerts'\n";

        IntegrationConfig.IntegrationSettings settings = integrationConfig.load(yaml);

        assertFalse(settings.isRetryEnabled());
        assertEquals(0, settings.getMaxRetries());
    }

    @Test
    void load_handlesMinimalConfig() {
        String yaml = "webhookUrl: https://minimal.example.com\n" +
                "timeoutSeconds: 5\n" +
                "retryEnabled: false\n" +
                "maxRetries: 0\n";

        IntegrationConfig.IntegrationSettings settings = integrationConfig.load(yaml);

        assertEquals("https://minimal.example.com", settings.getWebhookUrl());
        assertEquals(5, settings.getTimeoutSeconds());
        assertNull(settings.getSlackChannel());
    }
}
