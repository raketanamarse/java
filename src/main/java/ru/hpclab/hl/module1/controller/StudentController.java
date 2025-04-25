package ru.hpclab.hl.module1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Student;
import ru.hpclab.hl.module1.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.hpclab.hl.module1.service.ObservabilityService;
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
        UUID timerId = observabilityService.startTimer(operation); // старт

        List<Student> result = new ArrayList<>(studentRepository.findAll());

        observabilityService.stopTimer(timerId); // стоп
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        String operation = "getStudent_id";
        UUID timerId = observabilityService.startTimer(operation); // старт

        ResponseEntity<Student> response = studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        observabilityService.stopTimer(timerId); // стоп
        return response;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        String operation = "createStudent";
        UUID timerId = observabilityService.startTimer(operation); // старт

        Student savedStudent = studentRepository.save(student);

        observabilityService.stopTimer(timerId); // стоп
        return ResponseEntity.ok(savedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        String operation = "deleteStudent_id";
        UUID timerId = observabilityService.startTimer(operation); // старт

        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            observabilityService.stopTimer(timerId); // стоп
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            observabilityService.stopTimer(timerId); // стоп
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllStudents() {
        String operation = "deleteAllStudents";
        UUID timerId = observabilityService.startTimer(operation); // старт

        studentRepository.deleteAll();

        observabilityService.stopTimer(timerId); // стоп
        return ResponseEntity.ok().build();
    }
}


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
