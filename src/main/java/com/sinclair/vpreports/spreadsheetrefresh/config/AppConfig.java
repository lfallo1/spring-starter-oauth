package com.sinclair.vpreports.spreadsheetrefresh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${vpreports.db.connectionstring}")
    private String connectionString;

    @Value("${vpreports.programscontracts.path}")
    private String programsContractsFilePath;

    public String getConnectionString() {
        return connectionString;
    }

    public String getProgramsContractsFilePath() {
        return programsContractsFilePath;
    }
}
