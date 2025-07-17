package com.springdemo.springbasictoadvanced.repository;

import com.springdemo.springbasictoadvanced.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

