package com.springdemo.springbasictoadvanced.service;

import com.springdemo.springbasictoadvanced.dto.CourseDTO;
import com.springdemo.springbasictoadvanced.dto.OrderDTO;
import com.springdemo.springbasictoadvanced.dto.StudentDTO;
import com.springdemo.springbasictoadvanced.entity.Course;
import com.springdemo.springbasictoadvanced.entity.Order;
import com.springdemo.springbasictoadvanced.entity.Student;
import com.springdemo.springbasictoadvanced.exception.ResourceNotFoundException;
import com.springdemo.springbasictoadvanced.repository.CourseRepository;
import com.springdemo.springbasictoadvanced.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = toEntity(studentDTO);
        Student saved = studentRepository.save(student);
        return toDTO(saved);
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return toDTO(student);
    }

    public Page<StudentDTO> getAllStudentsPaged(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::toDTO);
    }

    public List<StudentDTO> findByNameContainsJPQL(String name) {
        return studentRepository.findByNameContainsJPQL(name).stream().map(this::toDTO).toList();
    }

    public StudentDTO findByEmailNative(String email) {
        Student student = studentRepository.findByEmailNative(email);
        return toDTO(student);
    }

    public List<StudentDTO> getAllStudentsWithCourse() {
        return studentRepository.findAllWithCourse().stream().map(this::toDTO).toList();
    }

    private StudentDTO toDTO(Student student) {
        if (student == null) return null;
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());
        dto.setOrder(toOrderDTO(student.getOrder()));
        dto.setCourse(toCourseDTO(student.getCourse()));
        return dto;
    }

    private OrderDTO toOrderDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTotalBill(order.getTotalBill());
        return dto;
    }

    private CourseDTO toCourseDTO(Course course) {
        if (course == null) return null;
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        return dto;
    }

    private Student toEntity(StudentDTO dto) {
        if (dto == null) return null;
        Student student = new Student();
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        student.setOrder(toOrderEntity(dto.getOrder()));
        student.setCourse(toCourseEntity(dto.getCourse()));
        return student;
    }

    private Order toOrderEntity(OrderDTO dto) {
        if (dto == null) return null;
        Order order = new Order();
        order.setId(dto.getId());
        order.setTotalBill(dto.getTotalBill());
        return order;
    }

    private Course toCourseEntity(CourseDTO dto) {
        if (dto == null) return null;
        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        return course;
    }
}