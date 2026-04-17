package com.nexustrack.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataSerializerTest {

    private DataSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new DataSerializer();
    }

    @Test
    void deserialize_parsesValidJson() throws JsonProcessingException {
        String json = "{\"name\":\"NexusTrack\",\"version\":1}";
        TestDto result = serializer.deserialize(json, TestDto.class);

        assertEquals("NexusTrack", result.getName());
        assertEquals(1, result.getVersion());
    }

    @Test
    void deserialize_ignoresUnknownProperties() throws JsonProcessingException {
        String json = "{\"name\":\"NexusTrack\",\"version\":1,\"extraField\":\"should be ignored\",\"anotherExtra\":42}";
        TestDto result = serializer.deserialize(json, TestDto.class);

        assertEquals("NexusTrack", result.getName());
        assertEquals(1, result.getVersion());
    }

    @Test
    void serialize_producesCorrectKeys() throws JsonProcessingException {
        TestDto dto = new TestDto();
        dto.setName("Alpha");
        dto.setVersion(3);

        String json = serializer.serialize(dto);

        assertTrue(json.contains("\"name\""));
        assertTrue(json.contains("\"Alpha\""));
        assertTrue(json.contains("\"version\""));
        assertTrue(json.contains("3"));
    }

    @Test
    void roundtrip_preservesAllFields() throws JsonProcessingException {
        TestDto original = new TestDto();
        original.setName("Roundtrip Test");
        original.setVersion(42);

        String json = serializer.serialize(original);
        TestDto restored = serializer.deserialize(json, TestDto.class);

        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getVersion(), restored.getVersion());
    }

    @Test
    void prettyPrint_producesFormattedOutput() throws JsonProcessingException {
        TestDto dto = new TestDto();
        dto.setName("Pretty");
        dto.setVersion(1);

        String pretty = serializer.prettyPrint(dto);

        assertTrue(pretty.contains("\n"));
        assertTrue(pretty.contains("Pretty"));
    }

    public static class TestDto {
        private String name;
        private int version;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getVersion() { return version; }
        public void setVersion(int version) { this.version = version; }
    }
}
