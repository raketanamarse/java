package ru.hpclab.hl.module1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "enrollments")
@JsonInclude(JsonInclude.Include.NON_NULL) // Игнорируем null-поля
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"enrollments"}) // Игнорируем enrollments в Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @JsonIgnoreProperties({"enrollments"}) // Игнорируем enrollments в Course
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private LocalDateTime enrollmentDate;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}

//package ru.hpclab.hl.module1.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@Table(name = "enrollments")
//public class Enrollment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private Student student;
//
//    @ManyToOne(optional = false) // обязательная связь
//    @JoinColumn(name = "course_id", nullable = false)
//    private Course course;
//
//    private LocalDateTime enrollmentDate;
//
//    @Enumerated(EnumType.STRING)
//    private EnrollmentStatus status;
//}
