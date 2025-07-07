package com.springdemo.springbasictoadvanced.service;

import com.springdemo.springbasictoadvanced.dto.OrderDTO;
import com.springdemo.springbasictoadvanced.dto.StudentDTO;
import com.springdemo.springbasictoadvanced.entity.Order;
import com.springdemo.springbasictoadvanced.entity.Student;
import com.springdemo.springbasictoadvanced.exception.ResourceNotFoundException;
import com.springdemo.springbasictoadvanced.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

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

    private StudentDTO toDTO(Student student) {
        if (student == null) return null;
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());
        dto.setOrder(toOrderDTO(student.getOrder()));
        return dto;
    }

    private OrderDTO toOrderDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTotalBill(order.getTotalBill());
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
        return student;
    }

    private Order toOrderEntity(OrderDTO dto) {
        if (dto == null) return null;
        Order order = new Order();
        order.setId(dto.getId());
        order.setTotalBill(dto.getTotalBill());
        return order;
    }
}