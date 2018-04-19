package com.sinclair.vpreports.spreadsheetrefresh.config.exception;

public class ReportException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private CustomErrorMessage error;

    public ReportException(CustomErrorMessage error) {
        this.error = error;
    }

    @Override
    public CustomErrorMessage getError() {
        return this.error;
    }
}
