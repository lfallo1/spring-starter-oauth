package com.sinclair.vpreports.spreadsheetrefresh.reports.service;

import com.sinclair.vpreports.spreadsheetrefresh.config.AppConfig;
import com.sinclair.vpreports.spreadsheetrefresh.config.event.ReportEventPublisher;
import com.sinclair.vpreports.spreadsheetrefresh.config.exception.CustomErrorMessage;
import com.sinclair.vpreports.spreadsheetrefresh.config.exception.ReportException;
import com.sinclair.vpreports.spreadsheetrefresh.reports.domain.GeneralContract;
import com.sinclair.vpreports.spreadsheetrefresh.reports.event.ReportErrorEvent;
import com.sinclair.vpreports.spreadsheetrefresh.reports.event.ReportErrorMessage;
import com.sinclair.vpreports.spreadsheetrefresh.reports.repository.ProgramRepository;
import com.sinclair.vpreports.spreadsheetrefresh.util.ExcelUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

@Service
public class ProgramService {

    private static final Logger LOGGER = LogManager.getLogger(ProgramService.class);

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ExcelUtil excelUtil;

    @Autowired
    private ReportEventPublisher reportEventPublisher;

    /**
     * update program names on the 'Programs' sheet of the ProgramsContracts.xlsx workbook
     */
    public void updateProgramNames() throws ReportException {

        FileOutputStream fileOut = null;

        //create input stream
        try (InputStream inp = new FileInputStream(new File(appConfig.getProgramsContractsFilePath()))) {
            //open the workbook & create a worksheet eval object used to evaluate cell values
            XSSFWorkbook wb = new XSSFWorkbook(OPCPackage.open(inp));
            XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);

            //load the 'Programs' tab, and then iterate over each row
            Sheet sheet = wb.getSheet("Programs");
            Map<Integer, String> headers = null;
            for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                //if the header map has not been populated, then populate it on the first pass
                if (null == headers) {
                    headers = excelUtil.generateHeaderRow(evaluator, row);
                } else {

                    //if not setting the header, then loop each cell in the row looking for the id column
                    for (int j = 0; j < row.getLastCellNum(); j++) {

                        //TODO replace dummy logic (currently finds value of cell with the header row of '#', assumes this is an ID and then performs a query on that value)
                        if ("#".equals(headers.get(j))) {

                            //get the cell's value
                            Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            CellValue value = evaluator.evaluate(cell);

                            try {
                                Integer id = value == null ? -1 : (int) value.getNumberValue();
                                GeneralContract generalContract = programRepository.findContractById(id);

                                if (null != generalContract) {
                                    excelUtil.setCellValue(headers, "Program Name", row, generalContract.getName());
                                    excelUtil.setCellValue(headers, "Owner", row, generalContract.getSyndicatorName());
                                    excelUtil.setCellValue(headers, "Category", row, generalContract.getProgramType());
                                }

                            } catch (NumberFormatException | NullPointerException e) {
                                LOGGER.error(e.toString());
                            }
                        }
                    }
                }
            }

            //write the changes to the file
            fileOut = new FileOutputStream(appConfig.getProgramsContractsFilePath());
            wb.write(fileOut);

        } catch (IOException | InvalidFormatException e) {
            LOGGER.error(e.toString());
            reportEventPublisher.publish(new ReportErrorEvent(new ReportErrorMessage(e.toString())));
            throw new ReportException(new CustomErrorMessage("reportError", e.toString()));
        } finally {

            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    LOGGER.error(e.toString());
                }
            }
        }
    }
}
