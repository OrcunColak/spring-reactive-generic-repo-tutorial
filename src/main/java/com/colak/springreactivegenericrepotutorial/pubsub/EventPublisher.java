package com.colak.springreactivegenericrepotutorial.pubsub;

import com.colak.springreactivegenericrepotutorial.model.StudentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

@Component
public class EventPublisher {

    private FluxSink<StudentEvent> fluxSink;
    private final Flux<StudentEvent> events;

    public EventPublisher() {
        events = Flux.create((FluxSink<StudentEvent> emitter) -> this.fluxSink = emitter)
                // broadcast
                .share();
    }

    public Mono<Void> publishEvent(StudentEvent event) {
        return Mono.fromRunnable(() -> this.fluxSink.next(event))
                // Return an empty Mono to represent completion
                .then();
    }

    public Flux<StudentEvent> getEvents() {
        return events;
    }
}
