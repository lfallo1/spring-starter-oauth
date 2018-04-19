package com.sinclair.vpreports.spreadsheetrefresh.reports.repository;

import com.sinclair.vpreports.spreadsheetrefresh.config.AppConfig;
import com.sinclair.vpreports.spreadsheetrefresh.reports.domain.GeneralContract;
import com.sinclair.vpreports.spreadsheetrefresh.reports.sql.ReportSQL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ProgramRepository implements ReportSQL {

    private static final Logger LOGGER = LogManager.getLogger(ProgramRepository.class);

    @Autowired
    private AppConfig appConfig;

    public GeneralContract findContractById(Integer generalContractID) {

        GeneralContract generalContract = null;

        try (Connection conn = DriverManager.getConnection(appConfig.getConnectionString())) {

            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(new SingleConnectionDataSource(conn, false));
            MapSqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("GeneralContractID", generalContractID);
            generalContract = jdbcTemplate.queryForObject(FIND_GENERALCONTRACT_BY_ID, paramSource, GENERAL_CONTRACT_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("No result for GeneralContractID: {}", generalContractID);
        } catch (DataAccessException | SQLException e) {
            LOGGER.warn(e.toString());
        }

        return generalContract;
    }
}
