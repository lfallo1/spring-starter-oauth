package com.sinclair.vpreports.spreadsheetrefresh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpreadsheetRefreshApplication {

    private static final Logger LOGGER = LogManager.getLogger(SpreadsheetRefreshApplication.class);

    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.toString());
        }

        SpringApplication.run(SpreadsheetRefreshApplication.class, args);
    }
}
