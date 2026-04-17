package com.nexustrack.report.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReportGeneratorTest {

    private ExcelReportGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ExcelReportGenerator();
    }

    @Test
    void generate_returnsNonEmptyByteArray() throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("name", "Project Alpha");
        row.put("status", "ACTIVE");
        rows.add(row);

        byte[] result = generator.generate(rows);

        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    void generate_startsWithXlsxMagicBytes() throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("title", "Task 1");
        rows.add(row);

        byte[] result = generator.generate(rows);

        assertEquals((byte) 0x50, result[0]);
        assertEquals((byte) 0x4B, result[1]);
    }

    @Test
    void generate_handlesEmptyRowList() throws IOException {
        List<Map<String, Object>> rows = Collections.emptyList();

        byte[] result = generator.generate(rows);

        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    void generate_handlesLargeDataset() throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", i);
            row.put("name", "Item " + i);
            row.put("value", Math.random() * 1000);
            rows.add(row);
        }

        byte[] result = generator.generate(rows);

        assertNotNull(result);
        assertTrue(result.length > 0);
        assertEquals((byte) 0x50, result[0]);
        assertEquals((byte) 0x4B, result[1]);
    }
}
