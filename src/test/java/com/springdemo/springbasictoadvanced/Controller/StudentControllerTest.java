package com.springdemo.springbasictoadvanced.Controller;

import com.springdemo.springbasictoadvanced.entity.Student;
import com.springdemo.springbasictoadvanced.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/students";
    }

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("John Doe");
        student.setEmail("john.doe@example.com");
        student.setAge(25);

        ResponseEntity<Student> response = restTemplate.postForEntity(baseUrl, student, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setName("Jane Doe");
        student.setEmail("jane.doe@example.com");
        student.setAge(22);
        Student savedStudent = studentRepository.save(student);

        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl + "/" + savedStudent.getId(), Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(savedStudent.getId());
        assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void testGetStudentById_NotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Student not found with id: 999");
    }
}