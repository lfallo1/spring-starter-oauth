package com.sinclair.vpreports.spreadsheetrefresh.reports.event;

import org.springframework.context.ApplicationEvent;

public class ReportErrorEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ReportErrorEvent(ReportErrorMessage source) {
        super(source);
    }
}
