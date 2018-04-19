package com.sinclair.vpreports.spreadsheetrefresh.reports.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ReportErrorEventListener implements ApplicationListener<ReportErrorEvent> {

    private static final Logger LOGGER = LogManager.getLogger(ReportErrorEventListener.class);

    @Override
    public void onApplicationEvent(ReportErrorEvent event) {
        try {
            ReportErrorMessage message = (ReportErrorMessage) event.getSource();
            LOGGER.info("Report Error {} - {}", message.getDate().toString(), message.getError());

            //TODO send notification / store in db

        } catch (ClassCastException | NullPointerException e) {
            LOGGER.error(e.toString());
        }
    }
}
