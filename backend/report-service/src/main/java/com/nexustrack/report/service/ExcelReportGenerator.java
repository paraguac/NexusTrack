package com.nexustrack.report.service;

import com.nexustrack.common.AppLogger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ExcelReportGenerator {

    private final AppLogger logger = new AppLogger(ExcelReportGenerator.class);

    public byte[] generate(List<Map<String, Object>> rows) throws IOException {
        logger.info("Generating Excel report with {} rows", rows.size());

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Report");

            if (!rows.isEmpty()) {
                Map<String, Object> firstRow = rows.get(0);
                String[] headers = firstRow.keySet().toArray(new String[0]);

                XSSFRow headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                for (int rowIdx = 0; rowIdx < rows.size(); rowIdx++) {
                    XSSFRow dataRow = sheet.createRow(rowIdx + 1);
                    Map<String, Object> row = rows.get(rowIdx);
                    for (int colIdx = 0; colIdx < headers.length; colIdx++) {
                        Object value = row.get(headers[colIdx]);
                        dataRow.createCell(colIdx).setCellValue(value != null ? value.toString() : "");
                    }
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}
