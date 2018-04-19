package com.sinclair.vpreports.spreadsheetrefresh.config.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class ReportEventPublisher extends CustomApplicationEventPublisher {

    private static final Logger LOGGER = LogManager.getLogger(ReportEventPublisher.class);

    @Override
    public void publish(ApplicationEvent event) {
        LOGGER.info("##ReportEventPublisher::publish => publishing error event ");
        super.publish(event);
    }
}
