package ru.hpclab.hl.module1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.*;
import ru.hpclab.hl.module1.repository.CourseRepository;
import ru.hpclab.hl.module1.repository.EnrollmentRepository;
import ru.hpclab.hl.module1.repository.StudentRepository;
import ru.hpclab.hl.module1.service.CourseStatisticsService;
import ru.hpclab.hl.module1.service.ObservabilityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseStatisticsService statisticsService;
    private final ObservabilityService observabilityService;

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        String operation = "getAllEnrollments";
        UUID timerId = observabilityService.startTimer(operation);

        List<Enrollment> enrollments = enrollmentRepository.findAll();

        observabilityService.stopTimer(timerId);
        return enrollments;
    }

    @PostMapping
    public ResponseEntity<Enrollment> enrollStudent(@RequestBody EnrollmentRequest request) {
        String operation = "enrollStudent";
        UUID timerId = observabilityService.startTimer(operation);

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        ResponseEntity<Enrollment> response = ResponseEntity.ok(enrollmentRepository.save(enrollment));

        observabilityService.stopTimer(timerId);
        return response;
    }

    @GetMapping("/by-course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
        String operation = "getEnrollmentsByCourse";
        UUID timerId = observabilityService.startTimer(operation);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);

        observabilityService.stopTimer(timerId);
        return enrollments;
    }

    @GetMapping("/by-student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        String operation = "getEnrollmentsByStudent";
        UUID timerId = observabilityService.startTimer(operation);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        observabilityService.stopTimer(timerId);
        return enrollments;
    }

    @GetMapping("/statistics/average-students")
    public Double getAverageStudentsPerCourse() {
        String operation = "getAverageStudentsPerCourse";
        UUID timerId = observabilityService.startTimer(operation);

        Double average = statisticsService.calculateAverageStudentsPerCourse();

        observabilityService.stopTimer(timerId);
        return average;
    }

    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollmentId) {
        String operation = "deleteEnrollment";
        UUID timerId = observabilityService.startTimer(operation);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollmentRepository.delete(enrollment);

        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        observabilityService.stopTimer(timerId);
        return response;
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllEnrollments() {
        String operation = "deleteAllEnrollments";
        UUID timerId = observabilityService.startTimer(operation);

        enrollmentRepository.deleteAll();

        ResponseEntity<Void> response = ResponseEntity.ok().build();
        observabilityService.stopTimer(timerId);
        return response;
    }
}


//package ru.hpclab.hl.module1.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.hpclab.hl.module1.model.*;
//import ru.hpclab.hl.module1.repository.CourseRepository;
//import ru.hpclab.hl.module1.repository.EnrollmentRepository;
//import ru.hpclab.hl.module1.repository.StudentRepository;
//import ru.hpclab.hl.module1.service.CourseStatisticsService;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/enrollments")
//@RequiredArgsConstructor
//public class EnrollmentController {
//    private final EnrollmentRepository enrollmentRepository;
//    private final StudentRepository studentRepository;
//    private final CourseRepository courseRepository;
//    private final CourseStatisticsService statisticsService;
//
//    @GetMapping
//    public List<Enrollment> getAllEnrollments() {
//        return enrollmentRepository.findAll();
//    }
//
//    @PostMapping
//    public ResponseEntity<Enrollment> enrollStudent(@RequestBody EnrollmentRequest request) {
//        Student student = studentRepository.findById(request.getStudentId())
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        Course course = courseRepository.findById(request.getCourseId())
//                .orElseThrow(() -> new RuntimeException("Course not found"));
//
//        Enrollment enrollment = new Enrollment();
//        enrollment.setStudent(student);
//        enrollment.setCourse(course);
//        enrollment.setEnrollmentDate(LocalDateTime.now());
//        enrollment.setStatus(EnrollmentStatus.ACTIVE);
//
//        return ResponseEntity.ok(enrollmentRepository.save(enrollment));
//    }
//
//    @GetMapping("/by-course/{courseId}")
//    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new RuntimeException("Course not found"));
//        return enrollmentRepository.findByCourse(course);
//    }
//
//    @GetMapping("/by-student/{studentId}")
//    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//        return enrollmentRepository.findByStudent(student);
//    }
//
//    @GetMapping("/statistics/average-students")
//    public Double getAverageStudentsPerCourse() {
//        return statisticsService.calculateAverageStudentsPerCourse();
//    }
//
//    @DeleteMapping("/{enrollmentId}")
//    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollmentId) {
//        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
//                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
//        enrollmentRepository.delete(enrollment);
//        return ResponseEntity.noContent().build();
//    }
//
//
//    @DeleteMapping("/all")
//    public ResponseEntity<Void> deleteAllEnrollments() {
//        enrollmentRepository.deleteAll();
//        return ResponseEntity.ok().build();
//    }
//}
