package com.colak.springreactivegenericrepotutorial.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record StudentEvent(String eventType, Student student) {
}
