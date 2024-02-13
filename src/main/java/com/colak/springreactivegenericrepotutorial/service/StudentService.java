package com.colak.springreactivegenericrepotutorial.service;

import com.colak.springreactivegenericrepotutorial.model.Student;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StudentService {

    private final Map<String, Student> studentDatabase = new ConcurrentHashMap<>();

    public Mono<Student> getStudentById(String id) {
        return Mono.justOrEmpty(studentDatabase.get(id));
    }

    public Mono<Student> createStudent(Student student) {
        // Assign a unique ID and save the student
        String studentId = UUID.randomUUID().toString();
        student.setId(studentId);
        studentDatabase.put(studentId, student);
        return Mono.just(student);
    }

    public Flux<Student> getAllStudents() {
        return Flux.fromIterable(studentDatabase.values());
    }
}
