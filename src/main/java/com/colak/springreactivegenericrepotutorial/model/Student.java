package com.colak.springreactivegenericrepotutorial.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Student {
    private String id;
    private String name;
    private int age;
}
