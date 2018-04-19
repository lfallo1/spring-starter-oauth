package com.sinclair.vpreports.spreadsheetrefresh.config.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public abstract class CustomApplicationEventPublisher implements ApplicationEventPublisherAware {

    protected ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publish(ApplicationEvent event) {
        this.publisher.publishEvent(event);
    }
}