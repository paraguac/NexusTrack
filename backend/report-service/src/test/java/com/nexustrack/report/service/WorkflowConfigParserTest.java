package com.nexustrack.report.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WorkflowConfigParserTest {

    private WorkflowConfigParser parser;

    @BeforeEach
    void setUp() {
        parser = new WorkflowConfigParser();
    }

    @Test
    void parse_createsWorkflowConfig() {
        String xml = "<workflow>" +
                "<name>Daily Report</name>" +
                "<schedule>0 8 * * *</schedule>" +
                "<enabled>true</enabled>" +
                "<step>fetch-data</step>" +
                "<step>generate-report</step>" +
                "<step>send-email</step>" +
                "</workflow>";

        WorkflowConfig config = parser.parse(xml);

        assertEquals("Daily Report", config.getName());
        assertEquals("0 8 * * *", config.getSchedule());
        assertTrue(config.isEnabled());
        assertEquals(3, config.getSteps().size());
        assertEquals("fetch-data", config.getSteps().get(0));
        assertEquals("generate-report", config.getSteps().get(1));
        assertEquals("send-email", config.getSteps().get(2));
    }

    @Test
    void serialize_producesValidXml() {
        WorkflowConfig config = new WorkflowConfig();
        config.setName("Weekly Summary");
        config.setSchedule("0 0 * * MON");
        config.setEnabled(false);
        config.setSteps(Arrays.asList("aggregate", "format", "publish"));

        String xml = parser.serialize(config);

        assertTrue(xml.contains("Weekly Summary"));
        assertTrue(xml.contains("0 0 * * MON"));
        assertTrue(xml.contains("aggregate"));
        assertTrue(xml.contains("format"));
        assertTrue(xml.contains("publish"));
    }

    @Test
    void roundtrip_preservesAllFields() {
        WorkflowConfig original = new WorkflowConfig();
        original.setName("Roundtrip Test");
        original.setSchedule("*/5 * * * *");
        original.setEnabled(true);
        original.setSteps(Arrays.asList("step-a", "step-b"));

        String xml = parser.serialize(original);
        WorkflowConfig restored = parser.parse(xml);

        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getSchedule(), restored.getSchedule());
        assertEquals(original.isEnabled(), restored.isEnabled());
        assertEquals(original.getSteps(), restored.getSteps());
    }
}
