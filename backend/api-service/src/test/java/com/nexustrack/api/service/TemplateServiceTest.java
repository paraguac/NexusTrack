package com.nexustrack.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemplateServiceTest {

    private TemplateService templateService;

    @BeforeEach
    void setUp() {
        templateService = new TemplateService();
    }

    @Test
    void render_substitutesVariables() {
        Map<String, String> vars = new HashMap<>();
        vars.put("greeting", "Hello");
        vars.put("target", "World");

        String result = templateService.render("${greeting}, ${target}!", vars);

        assertEquals("Hello, World!", result);
    }

    @Test
    void render_unknownVariablePassedThrough() {
        Map<String, String> vars = new HashMap<>();
        vars.put("known", "value");

        String result = templateService.render("${known} and ${unknown}", vars);

        assertEquals("value and ${unknown}", result);
    }

    @Test
    void render_emptyMap_returnsTemplateUnchanged() {
        String template = "No ${variables} here";
        String result = templateService.render(template, Collections.emptyMap());

        assertEquals("No ${variables} here", result);
    }

    @Test
    void renderProjectEvent_formatsCorrectly() {
        String result = templateService.renderProjectEvent("NexusTrack", "created", "alice");
        assertEquals("Project 'NexusTrack' was created by alice", result);
    }

    @ParameterizedTest
    @CsvSource({
        "Alpha,updated,bob,Project 'Alpha' was updated by bob",
        "Beta,deleted,carol,Project 'Beta' was deleted by carol",
        "Gamma,archived,dave,Project 'Gamma' was archived by dave"
    })
    void renderProjectEvent_parameterized(String name, String event, String actor, String expected) {
        String result = templateService.renderProjectEvent(name, event, actor);
        assertEquals(expected, result);
    }

    @Test
    void renderWebhookPayload_formatsCorrectly() {
        String result = templateService.renderWebhookPayload("MyProject", "https://example.com/hook");
        assertEquals("{\"project\": \"MyProject\", \"callback\": \"https://example.com/hook\"}", result);
    }
}
