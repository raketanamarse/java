package ru.hpclab.hl.module1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Student;
import ru.hpclab.hl.module1.repository.StudentRepository;
import ru.hpclab.hl.module1.service.ObservabilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentRepository studentRepository;
    private final ObservabilityService observabilityService;

    @GetMapping
    public List<Student> getAllStudents() {
        String operation = "getAllStudents";
        UUID timerId = observabilityService.startTimer(operation);

        List<Student> students = new ArrayList<>(studentRepository.findAll());

        observabilityService.stopTimer(timerId);
        return students;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        String operation = "getStudent";
        UUID timerId = observabilityService.startTimer(operation);

        ResponseEntity<Student> response = studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        observabilityService.stopTimer(timerId);
        return response;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        String operation = "createStudent";
        UUID timerId = observabilityService.startTimer(operation);

        Student savedStudent = studentRepository.save(student);
        ResponseEntity<Student> response = ResponseEntity.ok(savedStudent);

        observabilityService.stopTimer(timerId);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        String operation = "deleteStudent";
        UUID timerId = observabilityService.startTimer(operation);

        ResponseEntity<Void> response;
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            response = ResponseEntity.noContent().build();
        } else {
            response = ResponseEntity.notFound().build();
        }

        observabilityService.stopTimer(timerId);
        return response;
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllStudents() {
        String operation = "deleteAllStudents";
        UUID timerId = observabilityService.startTimer(operation);

        studentRepository.deleteAll();
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
//import ru.hpclab.hl.module1.model.Student;
//import ru.hpclab.hl.module1.repository.StudentRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/students")
//@RequiredArgsConstructor
//public class StudentController {
//    private final StudentRepository studentRepository;
//
//    @GetMapping
//    public List<Student> getAllStudents() {
//        return new ArrayList<>(studentRepository.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
//        return studentRepository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
//        Student savedStudent = studentRepository.save(student);
//        return ResponseEntity.ok(savedStudent);
//    }
//
//    //--------------------------------------------------------------------------------
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
//        if (studentRepository.existsById(id)) {
//            studentRepository.deleteById(id);
//            return ResponseEntity.noContent().build(); // HTTP 204
//        } else {
//            return ResponseEntity.notFound().build(); // HTTP 404
//        }
//    }
//
//    @DeleteMapping("/all")
//    public ResponseEntity<Void> deleteAllStudents() {
//        studentRepository.deleteAll();
//        return ResponseEntity.ok().build();
//    }
//}
//
