package com.sinclair.vpreports.spreadsheetrefresh.reports.sql;

import com.sinclair.vpreports.spreadsheetrefresh.reports.domain.GeneralContract;
import org.springframework.jdbc.core.RowMapper;

public interface ReportSQL {

    String FIND_GENERALCONTRACT_BY_ID = "select gc.GeneralContractID, gc.Name, pt.Type as ProgramType, syn.Name as SyndicatorName from GeneralContract gc inner join Syndicator syn on gc.SyndicatorID = syn.SyndicatorID inner join ProgramType pt on gc.ProgramTypeID = pt.ProgramTypeID where gc.GeneralContractID = :GeneralContractID;";

    RowMapper<GeneralContract> GENERAL_CONTRACT_ROW_MAPPER = (rs, rowNum) -> {
        GeneralContract result = new GeneralContract();
        result.setGeneralContractID(rs.getInt("GeneralContractID"));
        result.setName(rs.getString("Name"));
        result.setProgramType(rs.getString("ProgramType"));
        result.setSyndicatorName(rs.getString("SyndicatorName"));
        return result;
    };

}
