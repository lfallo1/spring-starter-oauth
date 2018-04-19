package com.sinclair.vpreports.spreadsheetrefresh.reports.schedule;

import com.sinclair.vpreports.spreadsheetrefresh.config.exception.ReportException;
import com.sinclair.vpreports.spreadsheetrefresh.reports.service.ProgramService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SpreadsheetScheduler {

    private static final Logger LOGGER = LogManager.getLogger(SpreadsheetScheduler.class);

    @Autowired
    private ProgramService programService;

    /**
     * TODO update fixed rate with a once per day task
     */
//    @Scheduled(initialDelay = 1000, fixedRate = 600000)
    public void updateProgramNames() {

        LOGGER.info("START updateProgramNames JOB {}", new Date().toString());

        //TODO handle error types differently
        try {
            programService.updateProgramNames();
        } catch (ReportException e) {
            LOGGER.error(e.toString());
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

        LOGGER.info("END updateProgramNames JOB {}", new Date().toString());
    }

}
