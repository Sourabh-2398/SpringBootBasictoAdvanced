package com.springdemo.springbasictoadvanced.service;

import com.springdemo.springbasictoadvanced.dto.CourseDTO;
import com.springdemo.springbasictoadvanced.entity.Course;
import com.springdemo.springbasictoadvanced.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        return course != null ? toDTO(course) : null;
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        Course saved = courseRepository.save(course);
        return toDTO(saved);
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) return null;
        course.setName(courseDTO.getName());
        Course updated = courseRepository.save(course);
        return toDTO(updated);
    }

    public boolean deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) return false;
        courseRepository.deleteById(id);
        return true;
    }

    private CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        return dto;
    }
}

