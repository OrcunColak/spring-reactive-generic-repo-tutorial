package com.colak.springreactivegenericrepotutorial.pubsub;

import com.colak.springreactivegenericrepotutorial.model.StudentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class EventSubscriber {


    @Autowired
    public EventSubscriber(EventPublisher eventPublisher) {
        eventPublisher.getEvents()
                .subscribe(
                        this::handleEvent,
                        throwable -> log.error("Error: " + throwable),
                        () -> log.info("Completed")
                );
    }

    private void handleEvent(StudentEvent event) {
        // Logic to handle the event (e.g., logging, additional processing)
        log.info("Received event: {}", event.eventType() + " - " + event.student());
    }
}
