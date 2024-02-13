package com.colak.springreactivegenericrepotutorial.controller;

import com.colak.springreactivegenericrepotutorial.model.Student;
import com.colak.springreactivegenericrepotutorial.pubsub.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EventPublisher eventPublisher;

    @Test
    void testCreateStudent() {
        Student newStudent = new Student(null, "Bob", 22);

        webTestClient.post()
                .uri("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newStudent))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Student.class)
                .value(student -> {
                    assertNotNull(student.getId());
                    assertEquals(newStudent.getName(), student.getName());
                    assertEquals(newStudent.getAge(), student.getAge());
                });

        // This does not work
        // Verify that the event was published
        // StepVerifier.create(eventPublisher.getEvents())
        //         .expectNextMatches(event -> "CREATE".equals(event.eventType()) && newStudent.equals(event.student()))
        //         .verifyComplete();
    }
}
