package com.sinclair.vpreports.spreadsheetrefresh.config.exception;

public abstract class AbstractException extends Exception{
    private static final long serialVersionUID = 1L;

    public abstract CustomErrorMessage getError();
}
