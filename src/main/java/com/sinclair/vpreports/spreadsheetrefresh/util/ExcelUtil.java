package com.sinclair.vpreports.spreadsheetrefresh.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExcelUtil {

    @Autowired
    private DataUtil dataUtil;

    /**
     * find a cell in a row, by its column index (determined by the name of the column & its position within a headers map) - and set the row's contents to the passed in value
     *
     * @param headers
     * @param columnName
     * @param row
     * @param value
     * @param <T>
     */
    public <T> void setCellValue(Map<Integer, String> headers, String columnName, Row row, T value) {
        Integer headerIdx = dataUtil.getKey(headers, columnName);
        if (null != headerIdx) {
            Cell programNameCell = row.getCell(headerIdx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (null != value) {
                programNameCell.setCellValue(value.toString());
            } else {
                programNameCell.setCellValue("");
            }
        }
    }

    public Map<Integer, String> generateHeaderRow(XSSFFormulaEvaluator evaluator, Row row) {
        Map<Integer, String> headers = new HashMap<>();
        int column = 0;
        for (Cell cell : row) {
            headers.put(column++, evaluator.evaluate(cell).getStringValue());
        }
        return headers;
    }

}
