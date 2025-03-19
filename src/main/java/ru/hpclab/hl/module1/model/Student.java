package ru.hpclab.hl.module1.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String studentId; // студенческий билет
    private Integer enrollmentYear; // год поступления

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
}
