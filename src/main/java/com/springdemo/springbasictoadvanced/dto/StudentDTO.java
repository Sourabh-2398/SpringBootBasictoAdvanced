package com.springdemo.springbasictoadvanced.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private OrderDTO order;
    private CourseDTO course;
}
