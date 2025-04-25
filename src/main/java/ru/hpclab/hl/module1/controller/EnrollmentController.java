package ru.hpclab.hl.module1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.*;
import ru.hpclab.hl.module1.repository.CourseRepository;
import ru.hpclab.hl.module1.repository.EnrollmentRepository;
import ru.hpclab.hl.module1.repository.StudentRepository;
import ru.hpclab.hl.module1.service.CourseStatisticsService;

import java.time.LocalDateTime;
import java.util.List;

import ru.hpclab.hl.module1.service.ObservabilityService;
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
        UUID timerId = observabilityService.startTimer(operation); // старт

        List<Enrollment> result = enrollmentRepository.findAll();

        observabilityService.stopTimer(timerId); // стоп
        return result;
    }

    @PostMapping
    public ResponseEntity<Enrollment> enrollStudent(@RequestBody EnrollmentRequest request) {
        String operation = "enrollStudent";
        UUID timerId = observabilityService.startTimer(operation); // старт

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        observabilityService.stopTimer(timerId); // стоп
        return ResponseEntity.ok(enrollmentRepository.save(enrollment));
    }

    @GetMapping("/by-course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
        String operation = "getEnrollmentsByCourse";
        UUID timerId = observabilityService.startTimer(operation); // старт

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        List<Enrollment> result = enrollmentRepository.findByCourse(course);

        observabilityService.stopTimer(timerId); // стоп
        return result;
    }

    @GetMapping("/by-student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        String operation = "getEnrollmentsByStudent";
        UUID timerId = observabilityService.startTimer(operation); // старт

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        List<Enrollment> result = enrollmentRepository.findByStudent(student);

        observabilityService.stopTimer(timerId); // стоп
        return result;
    }

    @GetMapping("/statistics/average-students")
    public Double getAverageStudentsPerCourse() {
        String operation = "getAverageStudentsPerCourse";
        UUID timerId = observabilityService.startTimer(operation); // старт

        Double average = statisticsService.calculateAverageStudentsPerCourse();

        observabilityService.stopTimer(timerId); // стоп
        return average;
    }

    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollmentId) {
        String operation = "deleteEnrollment";
        UUID timerId = observabilityService.startTimer(operation); // старт

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollmentRepository.delete(enrollment);

        observabilityService.stopTimer(timerId); // стоп
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllEnrollments() {
        String operation = "deleteAllEnrollments";
        UUID timerId = observabilityService.startTimer(operation); // старт

        enrollmentRepository.deleteAll();

        observabilityService.stopTimer(timerId); // стоп
        return ResponseEntity.ok().build();
    }
}




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
