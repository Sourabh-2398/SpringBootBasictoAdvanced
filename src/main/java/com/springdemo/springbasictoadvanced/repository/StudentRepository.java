package com.springdemo.springbasictoadvanced.repository;

import com.springdemo.springbasictoadvanced.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}