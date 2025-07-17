package com.springdemo.springbasictoadvanced.repository;

import com.springdemo.springbasictoadvanced.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Paging and sorting
    Page<Student> findAll(Pageable pageable);

    // JPQL custom query
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:name%")
    List<Student> findByNameContainsJPQL(@Param("name") String name);

    // Native query
    @Query(value = "SELECT * FROM student WHERE email = :email", nativeQuery = true)
    Student findByEmailNative(@Param("email") String email);

    // N+1 Problem:
    // In JPA/Hibernate, if you use LAZY loading for relationships (e.g., @ManyToOne(fetch = FetchType.LAZY)),
    // and fetch a list of Students, accessing student.getCourse() for each student triggers a separate SQL query per student (N+1 queries).
    // This is inefficient for large datasets.

    // Solution:
    // Use JPQL with JOIN FETCH to load related entities in a single query, e.g.:
     @Query("SELECT s FROM Student s JOIN FETCH s.course")
     List<Student> findAllWithCourse();
    //
    // This method fetches all students and their associated courses in a single query, solving the N+1 problem.
    // Note: Do NOT use this method with pagination (Pageable) as JOIN FETCH breaks paging in JPA/Hibernate.
    // For paging with relationships, use EntityGraph or batch fetching, or fetch IDs with pagination and then fetch related entities in a second query.

    // Pagination Issue:
    // JOIN FETCH with pagination (Pageable) is not supported directly by JPA/Hibernate and may cause incorrect results or exceptions.
    // For pagination with relationships, use EntityGraph or batch fetching, or fetch IDs with pagination and then fetch related entities in a second query.
}