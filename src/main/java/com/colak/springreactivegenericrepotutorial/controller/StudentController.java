package com.colak.springreactivegenericrepotutorial.controller;

import com.colak.springreactivegenericrepotutorial.model.Student;
import com.colak.springreactivegenericrepotutorial.model.StudentEvent;
import com.colak.springreactivegenericrepotutorial.pubsub.EventPublisher;
import com.colak.springreactivegenericrepotutorial.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private final EventPublisher eventPublisher;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Student>> getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id)
                .map(student -> ResponseEntity.ok(student))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Student>> createStudent(@RequestBody Student student) {
        return studentService.createStudent(student)
                .flatMap(createdStudent -> {
                    StudentEvent studentEvent = new StudentEvent("CREATE", createdStudent);
                    return eventPublisher.publishEvent(studentEvent).thenReturn(createdStudent);
                })
                .map(createdStudent -> ResponseEntity.status(HttpStatus.CREATED).body(createdStudent));
    }

    @GetMapping("/events")
    public Flux<StudentEvent> getAllEvents() {
        return eventPublisher.getEvents();
    }

    @GetMapping
    public Flux<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
