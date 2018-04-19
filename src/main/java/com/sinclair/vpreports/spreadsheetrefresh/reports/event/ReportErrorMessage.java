package com.sinclair.vpreports.spreadsheetrefresh.reports.event;

import java.util.Date;

public class ReportErrorMessage {

    private String error;
    private Date date;

    public ReportErrorMessage() {
    }

    public ReportErrorMessage(String error) {
        this.error = error;
        this.date = new Date();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
