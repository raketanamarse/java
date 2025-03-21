package ru.hpclab.hl.module1.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.hpclab.hl.module1.model.Enrollment;

import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseCode;
    private String name;
    private String lecturer;
    private Integer credits;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;
}